package com.luojia.netty.nettypro.netty.groupchat.demo2.server.session;

import io.netty.channel.Channel;

/**
 * 用户会话接口
 */
public interface Session {

    /**
     * 绑定会话，将用户名和会话绑定
     * @param channel 需要绑定的会话
     * @param username 会话绑定的用户名
     */
    void bind(Channel channel, String username);

    /**
     * 解绑会话
     * @param channel 需要解绑的会话 Channel
     */
    void unbind(Channel channel);

    /**
     * 根据用户名获取 Channel
     * @param username 用户名
     * @return
     */
    Channel getChannel(String username);

    /**
     * 获取属性
     * @param channel 哪个 channel
     * @param name 属性名
     * @return 属性值
     */
    Object getAttribute(Channel channel, String name);

    /**
     * 设置属性
     * @param channel 哪个 channel
     * @param name 属性名
     * @param value 属性值
     */
    void setAttribute(Channel channel, String name, Object value);

}
