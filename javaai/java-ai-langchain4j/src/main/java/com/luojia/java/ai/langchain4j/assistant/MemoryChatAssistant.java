package com.luojia.java.ai.langchain4j.assistant;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

/**
 * 让大模型有记忆能力
 */
@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "ollamaChatModel",
        chatMemory = "chatMemory"
)
public interface MemoryChatAssistant {

    String chat(String userMessage);
}
