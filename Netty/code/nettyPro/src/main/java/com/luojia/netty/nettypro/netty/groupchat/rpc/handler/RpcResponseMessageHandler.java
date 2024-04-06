package com.luojia.netty.nettypro.netty.groupchat.rpc.handler;

import com.luojia.netty.nettypro.netty.groupchat.rpc.message.RpcResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ChannelHandler.Sharable
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {

    // key 就是当前连接的序号，value是泛型，因为每个请求的返回类型可能不一样
    public static final Map<Integer, Promise<Object>> PROMISRS = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMessage msg) throws Exception {
        log.debug("msg: {}", msg);
        Promise<Object> promise = PROMISRS.remove(msg.getSequenceId());

        if (null != promise) {
            Object returnValue = msg.getReturnValue();
            Exception exceptionValue = msg.getExceptionValue();
            if (null != exceptionValue) {
                promise.setFailure(exceptionValue);
            } else {
                promise.setSuccess(returnValue);
            }
        }
    }
}
