package com.luojia.netty.nettypro.netty.groupchat.demo2.server.session;

import io.netty.channel.Channel;

import java.util.List;
import java.util.Set;

/**
 * 聊天组会话管理
 */
public interface GroupSession {

    /**
     * 创建一个聊天组，如果不存在才能创建成功
     * @param name 聊天组名称
     * @param member 成员
     * @return 成功时返回 null , 失败返回 原来的value，即 name不可重复
     */
    Group createGroup(String name, Set<String> member);

    /**
     * 移除聊天组
     * @param name 聊天组名称
     * @return 如果组不存在返回 null, 否则返回组对象
     */
    Group removeGroup(String name);

    /**
     * 加入聊天组
     * @param name 聊天组名称
     * @param member 新加入成员
     * @return 如果组不存在，返回null，否则返回组对象
     */
    Group joinMember(String name, String member);

    /**
     * 移除组成员
     * @param name 聊天组名称
     * @param member 成员名
     * @return 如果组不存在返回 null, 否则返回组对象
     */
    Group removeMember(String name, String member);

    /**
     * 获取组成员
     * @param name 聊天组名称
     * @return 成员集合, 没有成员会返回 empty set
     */
    Set<String> getMembers(String name);

    /**
     * 获取组成员 Channel 集合，只有在线的 Channel 才会返回
     * @param name 聊天组名称
     * @return 成员 channel 集合
     */
    List<Channel> getMembersChannel(String name);

}
