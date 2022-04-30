package com.wang.test;

import com.wang.rpc.api.HelloObject;
import com.wang.rpc.api.HelloService;
import com.wang.rpc.netty.server.NettyServer;
import com.wang.rpc.registry.DefaultServiceRegistry;
import com.wang.rpc.registry.ServiceRegistry;
import com.wang.rpc.serializer.KryoSerializer;
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
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.setSerializer(new ProtobufSerializer());
        server.start(9999);
    }
}
