package com.share.device.emqx.handler;

import com.alibaba.fastjson2.JSONObject;

public interface MessageHandler {

    void handleMessage(JSONObject message);
}
