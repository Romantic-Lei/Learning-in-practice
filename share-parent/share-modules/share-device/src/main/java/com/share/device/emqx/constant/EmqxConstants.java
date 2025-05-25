package com.share.device.emqx.constant;

/**
 * Emqx常量信息
 *
 */
public class EmqxConstants {


    /** 充电宝插入，柜机发布Topic消息， 服务器监听消息 */
    public final static String TOPIC_POWERBANK_CONNECTED = "/sys/powerBank/connected";

    /** 用户扫码，服务器发布Topic消息 柜机监听消息  */
    public final static String TOPIC_SCAN_SUBMIT = "/sys/scan/submit/%s";

    /** 充电宝弹出，柜机发布Topic消息，服务器监听消息  */
    public final static String TOPIC_POWERBANK_UNLOCK = "/sys/powerBank/unlock";

    /** 柜机属性上报，服务器监听消息  */
    public final static String TOPIC_PROPERTY_POST = "/sys/property/post";
}