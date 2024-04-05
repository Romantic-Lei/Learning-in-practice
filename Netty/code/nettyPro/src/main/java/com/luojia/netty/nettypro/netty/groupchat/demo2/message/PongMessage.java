package com.luojia.netty.nettypro.netty.groupchat.demo2.message;

public class PongMessage extends Message {
    @Override
    public int getMessageType() {
        return PongMessage;
    }
}
