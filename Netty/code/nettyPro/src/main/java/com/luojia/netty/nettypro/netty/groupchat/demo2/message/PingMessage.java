package com.luojia.netty.nettypro.netty.groupchat.demo2.message;

public class PingMessage extends Message {
    @Override
    public int getMessageType() {
        return PingMessage;
    }
}
