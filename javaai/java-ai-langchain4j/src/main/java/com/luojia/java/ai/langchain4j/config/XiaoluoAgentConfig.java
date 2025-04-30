package com.luojia.java.ai.langchain4j.config;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XiaoluoAgentConfig {

    @Bean
    public ChatMemoryProvider chatMemoryProviderXiaoLuo() {
        return memoryId -> MessageWindowChatMemory
                .builder()
                .id(memoryId)
                // 记忆20轮
                .maxMessages(20)
                // 设置持久化
                // .chatMemoryStore()
                .build();
     }
}
