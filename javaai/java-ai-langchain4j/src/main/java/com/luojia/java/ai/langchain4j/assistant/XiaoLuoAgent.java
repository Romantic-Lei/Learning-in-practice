package com.luojia.java.ai.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
//        chatModel = "ollamaChatModel",
        streamingChatModel = "ollamaStreamingChatModel",
        chatMemoryProvider = "chatMemoryProviderXiaoLuo",
        tools = "appointmentTools",
        contentRetriever = "contentRetrieverXiaoLuo"
)
public interface XiaoLuoAgent {

    @SystemMessage(fromResource = "xiaoluo-prompt-template.txt")
    Flux<String> chat(@MemoryId Long memoryId, @UserMessage String userMessage);

}
