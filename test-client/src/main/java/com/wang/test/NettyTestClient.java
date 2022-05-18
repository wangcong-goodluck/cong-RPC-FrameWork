package com.wang.test;

import com.wang.rpc.api.ByeService;
import com.wang.rpc.serializer.CommonSerializer;
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
        NettyClient client = new NettyClient(CommonSerializer.PROTOBUF_SERIALIZER);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This id a message");
        String res = helloService.hello(object);
        System.out.println(res);
        ByeService byeService = rpcClientProxy.getProxy(ByeService.class);
        System.out.println(byeService.bye("Netty"));
    }
}
