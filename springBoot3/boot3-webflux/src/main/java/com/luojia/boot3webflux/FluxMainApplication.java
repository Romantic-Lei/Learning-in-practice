package com.luojia.boot3webflux;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;
import java.net.URI;

public class FluxMainApplication {

    public static void main(String[] args) throws IOException {
        HttpHandler httpHandler = ((ServerHttpRequest request, ServerHttpResponse response) -> {
            // 1.编写业务逻辑
            URI uri = request.getURI();
            // 请求的uri：http://localhost:8080请求线程：Thread[reactor-http-nio-2,5,main]
            // 底层会使用异步线程
            System.out.println("请求的uri：" + uri + "请求线程：" + Thread.currentThread());

            DataBufferFactory factory = response.bufferFactory();
            DataBuffer buffer = factory.wrap(new String(uri.toString() + "hello!").getBytes());

            return response.writeWith(Mono.just(buffer));
        });

        // 2.启动服务，绑定8080端口，接受数据，拿到数据交给ReactorHttpHandlerAdapter处理
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);

        // 3.启动netty服务器
        HttpServer.create()
                .host("localhost")
                .port(8080)
                .handle(adapter)
                .bindNow();

        System.out.println("服务启动完成，开始监听8080  ---<<<");
        System.in.read();
        System.out.println("服务退出...");
    }
}
