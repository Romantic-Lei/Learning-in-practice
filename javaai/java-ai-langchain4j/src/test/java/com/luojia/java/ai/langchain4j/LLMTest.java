package com.luojia.java.ai.langchain4j;

import dev.langchain4j.model.LambdaStreamingResponseHandler;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LLMTest {

    @Test
    public void testGPTDemo() {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demp")
                .modelName("gpt-4o-mini")
                .build();
        String chat = model.chat("你好，大模型");
        System.out.println(chat);
    }

    @Autowired
    // private OpenAiChatModel openAiChatModel;
    private OllamaChatModel ollamaChatModel;
    @Test
    public void testSpringBoot() {
        String chat = ollamaChatModel.chat("你是什么大模型？");
        System.out.println(chat);
    }

    @Autowired
    private OllamaStreamingChatModel ollamaStreamingChatModel;
    @Test
    public void testOllamaStreamingChatModel() {
//        ollamaStreamingChatModel.generate("")
        System.out.println("---");
    }
}
