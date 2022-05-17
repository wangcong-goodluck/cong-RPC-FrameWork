package com.wang.rpc.transport.netty.client;

import com.wang.rpc.registry.NacosServiceDiscovery;
import com.wang.rpc.registry.NacosServiceRegistry;
import com.wang.rpc.registry.ServiceDiscovery;
import com.wang.rpc.registry.ServiceRegistry;
import com.wang.rpc.transport.RpcClient;
import com.wang.rpc.RpcMessageChecker;
import com.wang.rpc.entity.RpcRequest;
import com.wang.rpc.entity.RpcResponse;
import com.wang.rpc.enumeration.RpcError;
import com.wang.rpc.exception.RpcException;
import com.wang.rpc.transport.netty.server.NettyServer;
import com.wang.rpc.serializer.CommonSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

/**
 *  NIO 方式消费侧客户端类
 *
 * @author C.Wang
 * @CreateTime 2022/4/27 21:38
 */


public class NettyClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private static final EventLoopGroup group;
    private static final Bootstrap bootstrap;


    /**
     * 在静态代码块中直接配置好Netty客户端，等待发送数据时启动
     */
    static {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true);
    }

    private final ServiceDiscovery serviceDiscovery;
    private final CommonSerializer serializer;

    public NettyClient() {
        this(DEFAULT_SERIALIZER);
    }

    public NettyClient(Integer serializer) {
        this.serviceDiscovery = new NacosServiceDiscovery();
        this.serializer = CommonSerializer.getByCode(serializer);
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        AtomicReference<Object> result = new AtomicReference<>(null);
        try {
            InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
            Channel channel = ChannelProvider.get(inetSocketAddress, serializer);
            if (!channel.isActive()) {
                group.shutdownGracefully();
                return null;
            }
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
        } catch (InterruptedException e) {
            logger.error("发送消息时有错误发生：", e);
            Thread.currentThread().interrupt();
        }
        return result.get();
    }

}
