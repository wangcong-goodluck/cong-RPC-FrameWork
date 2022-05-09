package com.wang.test;

import com.wang.rpc.transport.RpcClientProxy;
import com.wang.rpc.api.HelloObject;
import com.wang.rpc.api.HelloService;
import com.wang.rpc.transport.netty.client.NettyClient;
import com.wang.rpc.serializer.ProtobufSerializer;

/**
 * 测试用Netty消费者
 *
 * @author C.Wang
 * @CreateTime 2022/4/27 23:37
 */


public class NettyTestClient {
    public static void main(String[] args) {
        NettyClient client = new NettyClient();
        client.setSerializer(new ProtobufSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject res = new HelloObject(12, "This id a message");
        System.out.println(res);
    }
}
