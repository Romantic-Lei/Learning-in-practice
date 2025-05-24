package com.share.device.emqx.factory;

import com.share.device.emqx.handler.MessageHandler;

public interface MessageHandlerFactory {

    MessageHandler getMassageHandler(String topic);
}