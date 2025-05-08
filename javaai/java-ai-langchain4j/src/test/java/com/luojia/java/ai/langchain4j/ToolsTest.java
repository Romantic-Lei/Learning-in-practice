package com.luojia.java.ai.langchain4j;

import com.luojia.java.ai.langchain4j.assistant.SeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ToolsTest {

    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testCalu() {
        String chat = separateChatAssistant.chat(1, "1+1等于几");
        // 大语言模型不擅长计算，如果这里给一个特别大的数，让它计算平方根，它会计算的不准确
        System.out.println(chat);
    }

}
