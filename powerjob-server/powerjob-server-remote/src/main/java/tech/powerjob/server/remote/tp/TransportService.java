package tech.powerjob.server.remote.tp;

import tech.powerjob.common.PowerSerializable;
import tech.powerjob.remote.framework.base.RemotingException;
import tech.powerjob.remote.framework.base.URL;

import java.util.concurrent.CompletionStage;

/**
 * server 数据传输服务
 *
 * @author tjq
 * @since 2023/1/21
 */
public interface TransportService {

    /**
     * 自用地址，用于维护 server -> appId 和 server 间通讯
     * 4.3.0 前为 ActorSystem Address（ip:10086）
     * 4.3.0 后 PowerJob 将主协议切换为 HTTP，使用 HTTP address （ip:10010）
     * @return 自用地址
     */
    ProtocolInfo defaultProtocol();

    void tell(String protocol, URL url, PowerSerializable request);

    <T> CompletionStage<T> ask(String protocol, URL url, PowerSerializable request, Class<T> clz) throws RemotingException;

}
