package com.luojia.java.ai.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "ollamaChatModel",
        chatMemoryProvider = "chatMemoryProviderXiaoLuo",
        tools = "appointmentTools",
        contentRetriever = "contentRetrieverXiaoLuo"
)
public interface XiaoLuoAgent {

    @SystemMessage(fromResource = "xiaoluo-prompt-template.txt")
    String chat(@MemoryId Long memoryId, @UserMessage String userMessage);

}
