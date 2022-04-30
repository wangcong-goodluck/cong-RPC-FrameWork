package com.wang.rpc.netty.client;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.wang.rpc.RpcClient;
import com.wang.rpc.RpcMessageChecker;
import com.wang.rpc.codec.CommonDecoder;
import com.wang.rpc.codec.CommonEncoder;
import com.wang.rpc.entity.RpcRequest;
import com.wang.rpc.entity.RpcResponse;
import com.wang.rpc.enumeration.RpcError;
import com.wang.rpc.exception.RpcException;
import com.wang.rpc.netty.server.NettyServer;
import com.wang.rpc.serializer.CommonSerializer;
import com.wang.rpc.serializer.JsonSerializer;
import com.wang.rpc.serializer.KryoSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author C.Wang
 * @CreateTime 2022/4/27 21:38
 */


public class NettyClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private static final Bootstrap bootstrap;

    private CommonSerializer serializer;

    /**
     * 在静态代码块中直接配置好Netty客户端，等待发送数据时启动
     */
    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true);
    }

    private String host;
    private int port;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        AtomicReference<Object> result = new AtomicReference<>(null);
        try {
            Channel channel = ChannelProvider.get(new InetSocketAddress(host, port), serializer);
            if (channel.isActive()) {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if (future1.isSuccess()) {
                        logger.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                    } else {
                        logger.error("发送消息时有错误发生：", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                //通过AttributeKey的方式阻塞获得返回结果
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse" + rpcRequest.getRequestId());
                RpcResponse rpcResponse = channel.attr(key).get();
                RpcMessageChecker.check(rpcRequest, rpcResponse);
                result.set(rpcResponse.getData());
            } else {
                System.exit(0);
            }
        } catch (InterruptedException e) {
            logger.error("发送消息时有错误发生：", e);
        }
        return result.get();
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
