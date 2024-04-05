package com.luojia.netty.nettypro.netty.groupchat.demo2.server.session;

public abstract class SessionFactory {

    private static Session session = new SessionMemoryImpl();

    public static Session getSession() {
        return session;
    }
}
