package tech.powerjob.remote.http.vertx;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.*;
import io.vertx.core.json.JsonObject;
import lombok.SneakyThrows;
import tech.powerjob.common.PowerSerializable;
import tech.powerjob.common.request.ServerScheduleJobReq;
import tech.powerjob.remote.framework.base.Address;
import tech.powerjob.remote.framework.base.HandlerLocation;
import tech.powerjob.remote.framework.base.RemotingException;
import tech.powerjob.remote.framework.base.URL;
import tech.powerjob.remote.framework.transporter.Protocol;
import tech.powerjob.remote.framework.transporter.Transporter;
import tech.powerjob.remote.http.HttpProtocol;

import java.util.concurrent.CompletionStage;

/**
 * VertxTransporter
 *
 * @author tjq
 * @since 2023/1/1
 */
public class VertxTransporter implements Transporter {

    private final HttpClient httpClient;

    private static final Protocol PROTOCOL = new HttpProtocol();

    public VertxTransporter(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Protocol getProtocol() {
        return PROTOCOL;
    }

    @Override
    public void tell(URL url, PowerSerializable request) {
        post(url, request, null);
    }

    @Override
    public <T> CompletionStage<T> ask(URL url, PowerSerializable request, Class<T> clz) throws RemotingException {
        return post(url, request, clz);
    }

    private <T> CompletionStage<T> post(URL url, PowerSerializable request, Class<T> clz) {
        final String host = url.getAddress().getHost();
        final int port = url.getAddress().getPort();
        final String path = url.getLocation().toPath();
        RequestOptions requestOptions = new RequestOptions()
                .setMethod(HttpMethod.POST)
                .setHost(host)
                .setPort(port)
                .setURI(path);
        // 获取远程服务器的HTTP连接
        Future<HttpClientRequest> httpClientRequestFuture = httpClient.request(requestOptions);
        // 转换 -> 发送请求获取响应
        Future<HttpClientResponse> responseFuture = httpClientRequestFuture.compose(httpClientRequest -> httpClientRequest.send(JsonObject.mapFrom(request).toBuffer()));
        return responseFuture.compose(httpClientResponse -> {
            // throw exception
            final int statusCode = httpClientResponse.statusCode();
            if (statusCode != HttpResponseStatus.OK.code()) {
                // CompletableFuture.get() 时会传递抛出该异常
                throw new RemotingException(String.format("request [host:%s,port:%s,url:%s] failed, status: %d, msg: %s",
                       host, port, path, statusCode, httpClientResponse.statusMessage()
                        ));
            }

            // TODO: 验证无响应的情况

            return httpClientResponse.body().compose(x -> Future.succeededFuture(x.toJsonObject().mapTo(clz)));
        }).toCompletionStage();
    }
}
