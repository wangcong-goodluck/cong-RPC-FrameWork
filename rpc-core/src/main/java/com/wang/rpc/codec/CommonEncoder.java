package com.wang.rpc.codec;

import com.wang.rpc.entity.RpcRequest;
import com.wang.rpc.enumeration.PackageType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import com.wang.rpc.serializer.CommonSerializer;

/**
 * 通用的编码拦截器
 *
 * @author C.Wang
 * @CreateTime 2022/4/27 22:02
 */

/**
 * CommonEncoder继承了MessageToByteEncoder类，就是把Message(实际要发送的对象)转换成Byte数组。
 * CommonEncoder的工作就是把RpcRequest或RpcResponse包装成协议包，根据一定的协议格式，将各个字段写到管道里就可以了。
 */
public class CommonEncoder extends MessageToByteEncoder {

    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    private final CommonSerializer serializer;

    public CommonEncoder(CommonSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeInt(MAGIC_NUMBER);
        if (msg instanceof RpcRequest) {
            out.writeInt(PackageType.REQUEST_PACK.getCode());
        } else {
            out.writeInt(PackageType.RESPONSE_PACK.getCode());
        }
        out.writeInt(serializer.getCode());//获取序列化器的编号
        //之后使用传入的序列化器将请求或响应包序列化为字节数组写入管道即可。
        byte[] bytes = serializer.serialize(msg);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
