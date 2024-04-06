package com.luojia.netty.nettypro.netty.groupchat.demo2.protocol;

import com.luojia.netty.nettypro.netty.groupchat.demo2.config.Config;
import com.luojia.netty.nettypro.netty.groupchat.demo2.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * #################################################################################################
 * ##########                  【自定义】消息 编解码 类   【 支持@Sharable 】                   ########
 * ##########   父类 MessageToMessageCodec 认为是完整的信息 【所以必须保证上一个处理器是 帧解码器】 ########
 * #################################################################################################
 * 相当于两个handler合二为一，【既能入站 也能做出站处理】
 *  <b>魔数     </b>，用来在第一时间判定是否是无效数据包
 *  <b>版本号   </b>，可以支持协议的升级
 *  <b>序列化算法</b>，消息正文到底采用哪种序列化反序列化方式，可以由此扩展，例如：json、protobuf、hessian、jdk
 *  <b>指令类型  </b>，是登录、注册、单聊、群聊... 跟业务相关
 *  <b>请求序号  </b>，为了双工通信，提供异步能力
 *  <b>正文长度  </b>
 *  <b>消息正文  </b>
 */
// 写这个类 肯定的认为 上一个处理器 是 帧解码器，所以不用考虑半包黏包问题，直接解码拿数据
@Slf4j
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> outList) throws Exception {
        ByteBuf out = ctx.alloc().buffer();

        out.writeBytes(new byte[]{1,2,3,4}); // 4字节的 魔数
        out.writeByte(1);                    // 1字节的 版本
        // 枚举对象内部维护了一个顺序 ordinal，按照声明顺序从0开始往后排序
        out.writeByte(Config.getMySerializerAlgorithm().ordinal()); // 1字节的 序列化方式 0-jdk,1-json
        out.writeByte(msg.getMessageType()); // 1字节的 指令类型
        out.writeInt(msg.getSequenceId());   // 4字节的 请求序号 【大端】
        out.writeByte(0xff);                 // 1字节的 对其填充，只为了非消息内容 是2的整数倍

// HEAD >>>>>>>>>>>>>>>
/*        // 处理内容 用对象流包装字节数组 并写入
        ByteArrayOutputStream bos = new ByteArrayOutputStream(); // 访问数组
        ObjectOutputStream oos = new ObjectOutputStream(bos);    // 用对象流 包装
        oos.writeObject(msg);

        byte[] bytes = bos.toByteArray();*/
// ====================
        final byte[] bytes = Config.getMySerializerAlgorithm().serializ(msg);
// <<<<<<<<<<<<<<<<<<<<

        // 写入内容 长度
        out.writeInt(bytes.length);
        // 写入内容
        out.writeBytes(bytes);

        /**
         * 加入List 方便传递给 下一个Handler
         */
        outList.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magicNum = in.readInt();        // 大端4字节的 魔数
        byte version = in.readByte();       // 版本
        byte serializerType = in.readByte();// 0 Java 1 Json
        byte messageType = in.readByte();
        int sequenceId = in.readInt();
        in.readByte();

        int length = in.readInt();
        final byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length); // 读取进来，下面再进行 解码

// HEAD >>>>>>>>>>>>>>>
/*        // 处理内容
        final ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        final ObjectInputStream ois = new ObjectInputStream(bis);

        // 转成 Message类型
        Message message = (Message) ois.readObject();*/
// ====================
        // 1. 找到反序列化算法
        final MySerializer.Algorithm algorithm = MySerializer.Algorithm.values()[serializerType];
        // 2. 找到消息具体类型
        final Object message = algorithm.deserializer(Message.getMessageClass(messageType), bytes);

// <<<<<<<<<<<<<<<<<<<<

//        log.debug("{},{},{},{},{},{}",magicNum, version, serializerType, messageType, sequenceId, length);
        log.debug("{}", message);

        /**
         * 加入List 方便传递给 下一个Handler
         */
        out.add(message);
    }
}
