package com.luojia.java.ai.langchain4j;

import com.luojia.java.ai.langchain4j.assistant.SeparateChatAssistant;
import com.luojia.java.ai.langchain4j.assistant.XiaoLuoAgent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest
public class ToolsTest {

    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testCalu() {
        String chat = separateChatAssistant.chat(1, "1+1等于几,475695037565的平方根是多少？");
        // 大语言模型不擅长计算，如果这里给一个特别大的数，让它计算平方根，它会计算的不准确
        // 如果需要计算准确，需要自己写一个工具类，大模型会自动调用
        // 1+1等于2。 475695037565的平方根是约689706.4865。
        System.out.println(chat);
    }

    @Autowired
    private XiaoLuoAgent xiaoLuoAgent;
    @Test
    public void testGuahao() {
        Flux<String> chat = xiaoLuoAgent.chat(1l, "预约挂号,姓名：张三，身份证： 123594565485652142，科室：神经内科、时间：明天上午，医生：张医生");
        // 大语言模型不擅长计算，如果这里给一个特别大的数，让它计算平方根，它会计算的不准确
        // 如果需要计算准确，需要自己写一个工具类，大模型会自动调用
        // 1+1等于2。 475695037565的平方根是约689706.4865。
//        chat.subscribe(System.out::println);
        System.out.println(chat);
    }

}
