package com.luojia.java.ai.langchain4j;

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
    private OpenAiChatModel openAiChatModel;
    @Test
    public void testSpringBoot() {
        String chat = openAiChatModel.chat("你是什么大模型？");
        System.out.println(chat);
    }
}
