package com.luojia.java.ai.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

/**
 * chatMemoryProvider:会话隔离
 */
@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "ollamaChatModel",
        chatMemoryProvider = "chatMemoryProvider",
        tools = "calculatorTools" // 工具类
)
public interface SeparateChatAssistant {

    String chat(@MemoryId int memoryId, @UserMessage String userMessage);
}
