package com.luojia.java.ai.langchain4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luojia.java.ai.langchain4j.assistant.XiaoLuoAgent;
import com.luojia.java.ai.langchain4j.bean.ChatForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Map;

@Tag(name = "硅谷小骆")
@RestController
@RequestMapping("/xiaoluo")
public class XiaoluoController {

    @Autowired
    private XiaoLuoAgent xiaoLuoAgent;

    @Operation(summary = "对话")
    @PostMapping(value = "/chat", produces = "text/event-stream;charset=utf-8")
    public Flux<String> chat(@RequestBody ChatForm chatForm) {
//        return xiaoLuoAgent.chat(chatForm.getMemoryId(), chatForm.getMessage());
        return xiaoLuoAgent.chat(chatForm.getMemoryId(), chatForm.getMessage())
                .doOnNext(chunk -> System.out.println("发射数据: " + chunk)); // 日志
    }

    @PostMapping(value = "/chat-raw", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatRaw(@RequestBody ChatForm chatForm) {
        WebClient webClient = WebClient.create("http://localhost:11434");
        return webClient.post()
                .uri("/api/generate")
                .bodyValue(Map.of(
                        "model", "qwen3:4b",
                        "prompt", chatForm.getMessage(),
                        "stream", true
                ))
                .retrieve()
                .bodyToFlux(String.class)
                .map(json -> extractResponse(json)) // 从 Ollama 的 JSON 响应中提取内容
                .doOnNext(chunk -> System.out.println("RAW Chunk: " + chunk));
    }
    private String extractResponse(String json) {
        try {
            return new ObjectMapper().readTree(json).get("response").asText();
        } catch (Exception e) {
            return "[ERROR]";
        }
    }

}
