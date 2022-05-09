package com.wang.test;

import com.wang.rpc.api.HelloService;
import com.wang.rpc.transport.netty.server.NettyServer;
import com.wang.rpc.provider.ServiceProviderImpl;
import com.wang.rpc.registry.ServiceRegistry;
import com.wang.rpc.serializer.ProtobufSerializer;

/**
 *  测试用Netty服务提供者（服务端）
 *
 * @author C.Wang
 * @CreateTime 2022/4/27 23:30
 */


public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();

        NettyServer server = new NettyServer("127.0.0.1", 9999);
        server.setSerializer(new ProtobufSerializer());
        server.publishService(helloService, HelloService.class);
    }
}
