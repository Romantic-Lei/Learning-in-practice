package com.luojia.java.ai.langchain4j.bean;

import lombok.Data;

@Data
public class ChatForm {

    // 对话id
    private Long memoryId;

    // 用户问题
    private String message;
}