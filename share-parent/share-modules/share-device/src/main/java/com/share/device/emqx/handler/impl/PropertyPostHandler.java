package com.share.device.emqx.handler.impl;


import com.alibaba.fastjson2.JSONObject;
import com.share.device.emqx.annotation.JiaEmqx;
import com.share.device.emqx.handler.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@JiaEmqx(topic = com.share.device.emqx.constant.EmqxConstants.TOPIC_PROPERTY_POST)
public class PropertyPostHandler implements MessageHandler {

    @Override
    public void handleMessage(JSONObject message) {
        log.info("handleMessage: {}", message.toJSONString());
    }
}