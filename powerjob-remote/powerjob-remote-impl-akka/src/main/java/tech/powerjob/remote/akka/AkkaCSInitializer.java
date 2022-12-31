package tech.powerjob.remote.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.actor.Props;
import com.google.common.collect.Maps;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import tech.powerjob.remote.framework.actor.HandlerInfo;
import tech.powerjob.remote.framework.base.Address;
import tech.powerjob.remote.framework.base.ServerType;
import tech.powerjob.remote.framework.cs.CSInitializer;
import tech.powerjob.remote.framework.cs.CSInitializerConfig;
import tech.powerjob.remote.framework.transporter.Transporter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * AkkaCSInitializer
 *
 * @author tjq
 * @since 2022/12/31
 */
public class AkkaCSInitializer implements CSInitializer {

    private ActorSystem actorSystem;
    private CSInitializerConfig config;

    @Override
    public String type() {
        return AkkaConstant.PROTOCOL;
    }

    @Override
    public void init(CSInitializerConfig config) {

        this.config = config;

        Address bindAddress = config.getBindAddress();

        // 初始化 ActorSystem（macOS上 new ServerSocket 检测端口占用的方法并不生效，可能是AKKA是Scala写的缘故？没办法...只能靠异常重试了）
        Map<String, Object> overrideConfig = Maps.newHashMap();
        overrideConfig.put("akka.remote.artery.canonical.hostname", bindAddress.getHost());
        overrideConfig.put("akka.remote.artery.canonical.port", bindAddress.getPort());

        Config akkaBasicConfig = ConfigFactory.load(AkkaConstant.AKKA_CONFIG);
        Config akkaFinalConfig = ConfigFactory.parseMap(overrideConfig).withFallback(akkaBasicConfig);

        // 启动时绑定当前的 actorSystemName
        String actorSystemName = AkkaConstant.fetchActorSystemName(config.getServerType(), false);
        this.actorSystem = ActorSystem.create(actorSystemName, akkaFinalConfig);

        // 处理系统中产生的异常情况
        ActorRef troubleshootingActor = actorSystem.actorOf(Props.create(AkkaTroubleshootingActor.class), "troubleshooting");
        actorSystem.eventStream().subscribe(troubleshootingActor, DeadLetter.class);
    }

    @Override
    public Transporter buildTransporter() {
        return new AkkaTransporter(config.getServerType(), actorSystem);
    }

    @Override
    public void bindHandlers(List<HandlerInfo> handlerInfos) {

    }

    @Override
    public void close() throws IOException {
        actorSystem.terminate();
    }
}
