package com.luojia.demo.design.pattern.v2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Factory {

    private static Map<String, HandlerStrategyFactory> strategyMap = new ConcurrentHashMap<>();

    private static HandlerStrategyFactory getInvokeStrategy(String srt) {
        return strategyMap.get(srt);
    }

    public static void register(String str, HandlerStrategyFactory handler) {
        System.out.println("str: " + str + "\t handler: " + handler);
        if (null == str || null == handler) return;

        strategyMap.put(str, handler);
    }

}
