package com.luojia.java.ai.langchain4j.controller;

import com.luojia.java.ai.langchain4j.assistant.XiaoLuoAgent;
import com.luojia.java.ai.langchain4j.bean.ChatForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "硅谷小骆")
@RestController
@RequestMapping("/xiaoluo")
public class XiaoluoController {

    @Autowired
    private XiaoLuoAgent xiaoLuoAgent;

    @Operation(summary = "对话")
    @PostMapping("/chat")
    public String chat(@RequestBody ChatForm chatForm) {
        return xiaoLuoAgent.chat(chatForm.getMemoryId(), chatForm.getMessage());
    }

}
