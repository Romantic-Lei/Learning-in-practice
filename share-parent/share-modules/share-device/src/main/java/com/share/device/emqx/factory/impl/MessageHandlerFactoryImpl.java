package com.share.device.emqx.factory.impl;


import com.share.device.emqx.annotation.JiaEmqx;
import com.share.device.emqx.factory.MessageHandlerFactory;
import com.share.device.emqx.handler.MessageHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageHandlerFactoryImpl implements MessageHandlerFactory, ApplicationContextAware {


    private Map<String, MessageHandler> handlerMap = new HashMap<>();

    /**
     * 初始化bean对象
     *
     * @param ioc
     */
    @Override
    public void setApplicationContext(ApplicationContext ioc) {
        // 获取对象
        Map<String, MessageHandler> beanMap = ioc.getBeansOfType(MessageHandler.class);
        for (MessageHandler messageHandler : beanMap.values()) {
            JiaEmqx jiaEmqx = AnnotatedElementUtils.findAllMergedAnnotations(messageHandler.getClass(), JiaEmqx.class).iterator().next();
            if (null != jiaEmqx) {
                String topic = jiaEmqx.topic();
                // 初始化到map
                handlerMap.put(topic, messageHandler);
            }
        }
    }

    @Override
    public MessageHandler getMassageHandler(String topic) {
        return handlerMap.get(topic);
    }
}