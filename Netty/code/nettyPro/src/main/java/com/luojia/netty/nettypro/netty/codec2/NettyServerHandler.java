package com.luojia.netty.nettypro.netty.codec2;

import com.luojia.netty.nettypro.netty.codec.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 我们自定义一个Handler 需要继承netty 规定好的某个HandlerAdapter(规范)
 * 这时我们自定义一个Handler，才能称为一个handler
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.Mymessage> {

    /**
     * 读取数据（这里读取客户端发送的消息）
     * @param ctx 上下文对象，含有管道pipeline， 通道Channel，地址
     * @param msg 客户端发送的数据，默认Object
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Mymessage msg) throws Exception {
        MyDataInfo.Mymessage.DataType dataType = msg.getDataType();
        if (dataType == MyDataInfo.Mymessage.DataType.StudentType) {
            MyDataInfo.Student student = msg.getStudent();
            System.out.println("客户端发送的学生 id = " + student.getId() + " 学生名字 = " + student.getName());
        } else if (dataType == MyDataInfo.Mymessage.DataType.WorkerType) {
            MyDataInfo.Worker worker = msg.getWorker();
            System.out.println("客户端发送的工人年龄 = " + worker.getAge() + " 工人名字 = " + worker.getName());
        } else {
            System.out.println("传输的类型不正确");
        }
    }

    /**
     * 数据读取完后，执行的操作
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // writeAndFlush 是 write和flush
        // 将数据写入缓存并刷新，一般我们需要对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端", CharsetUtil.UTF_8));
    }

    // 处理异常，需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
