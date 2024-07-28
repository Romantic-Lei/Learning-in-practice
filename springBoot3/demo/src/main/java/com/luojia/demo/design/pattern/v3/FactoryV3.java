package com.luojia.demo.design.pattern.v3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FactoryV3 {

    private static Map<String, AbstractColaHandler> strategyMap = new ConcurrentHashMap<>();

    public static AbstractColaHandler getInvokeStrategy(String srt) {
        return strategyMap.get(srt);
    }

    public static void register(String str, AbstractColaHandler handler) {
        System.out.println("str: " + str + "\t handler: " + handler);
        if (null == str || null == handler) return;

        strategyMap.put(str, handler);
    }

}
