package com.luojia.java.ai.langchain4j;

import com.luojia.java.ai.langchain4j.assistant.Assistant;
import com.luojia.java.ai.langchain4j.assistant.MemoryChatAssistant;
import com.luojia.java.ai.langchain4j.assistant.SeparateChatAssistant;
import com.luojia.java.ai.langchain4j.config.SeparateChatAssistantConfig;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AiServiceTest {

    @Autowired
    private QwenChatModel qwenChatModel;

    @Test
    public void testChat() {
        Assistant assistant = AiServices.create(Assistant.class, qwenChatModel);
        String chat = assistant.chat("你是什么模型");
        System.out.println(chat);
    }

    @Autowired
    private Assistant assistant;
    @Test
    public void testAssistant() {
        String chat = assistant.chat("你是什么模型");
        System.out.println(chat);
    }

    @Autowired
    private MemoryChatAssistant memoryChatAssistant;
    @Test
    public void testAssistant1() {
        String answer1 = memoryChatAssistant.chat("我是小蹦蹦");
        System.out.println(answer1);
        String answer2 = memoryChatAssistant.chat("我是谁");
        System.out.println(answer2);
    }

    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testAssistant2() {
        String answer1 = separateChatAssistant.chat(1, "我是小蹦蹦");
        System.out.println(answer1);
        String answer2 = separateChatAssistant.chat(1,"我是谁");
        System.out.println(answer2);

        String answer3 = separateChatAssistant.chat(2, "我是谁");
        System.out.println(answer3);
    }
}
