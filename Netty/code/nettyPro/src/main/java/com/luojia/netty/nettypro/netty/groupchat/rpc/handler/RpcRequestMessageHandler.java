package com.luojia.netty.nettypro.netty.groupchat.rpc.handler;

import com.luojia.netty.nettypro.netty.groupchat.rpc.message.RpcRequestMessage;
import com.luojia.netty.nettypro.netty.groupchat.rpc.message.RpcResponseMessage;
import com.luojia.netty.nettypro.netty.groupchat.rpc.server.service.HelloService;
import com.luojia.netty.nettypro.netty.groupchat.rpc.server.service.ServiceFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@ChannelHandler.Sharable
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage message) {
        RpcResponseMessage response = new RpcResponseMessage();
        response.setSequenceId(message.getSequenceId());
        try {
            HelloService service = (HelloService)
                    ServiceFactory.getService(Class.forName(message.getInterfaceName()));
            Method method = service.getClass().getMethod(message.getMethodName(), message.getParameterTypes());
            Object invoke = method.invoke(service, message.getParameterValue());

            response.setReturnValue(invoke);
        } catch (Exception e) {
            e.printStackTrace();
            response.setExceptionValue(new Exception("远程调用出错" + e.getCause().getMessage()));
        }

        ctx.writeAndFlush(response).addListener(future -> {
            if(!future.isSuccess()){
                Throwable cause = future.cause();
                log.error("error : ", cause);
            }
        });
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        RpcRequestMessage message = new RpcRequestMessage(
                1,
                "com.luojia.netty.nettypro.netty.groupchat.rpc.server.service.HelloService",
                "sayHello",
                String.class,
                new Class[]{String.class},
                new Object[]{"张三"}
        );

        HelloService service = (HelloService)
                ServiceFactory.getService(Class.forName(message.getInterfaceName()));

        Method method = service.getClass().getMethod(message.getMethodName(), message.getParameterTypes());
        Object invoke = method.invoke(service, message.getParameterValue());
        System.out.println(invoke);
    }
}
