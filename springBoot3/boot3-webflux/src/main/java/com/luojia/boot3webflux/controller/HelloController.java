package com.luojia.boot3webflux.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class HelloController {

    @GetMapping("/string")
    public String string(@RequestParam("key") String key) {
        return "Hello World!!! key = " + key;
    }

    /**
     * 响应式编程虽然能兼容上面的返回，但是更加推荐下面的使用方式
     * 返回单个数据 mono
     * 返回多个数据 Flux
     * 配合Flux，完成服务端SSE：Server Send Event; 服务端事件推动
      */

    @GetMapping("/haha")
    public Mono<String> haha(@RequestParam("key") String key) {
        return Mono.just("Hello World!!! key = " + key);
    }

    @GetMapping("/hehe")
    public Flux<String> hehe() {
        return Flux.just("hehe1", "hehe2");
    }

    // ChatGpt 都在使用，服务端推送
    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sse() {
        return Flux.range(1, 10)
                .map(i -> "ha-" + i)
                .delayElements(Duration.ofMillis(200));
    }

}
