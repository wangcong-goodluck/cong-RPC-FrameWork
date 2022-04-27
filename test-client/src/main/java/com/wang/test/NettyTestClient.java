package com.wang.test;

import com.wang.rpc.RpcClientProxy;
import com.wang.rpc.api.HelloObject;
import com.wang.rpc.api.HelloService;
import com.wang.rpc.netty.client.NettyClient;

/**
 * 测试用Netty消费者
 *
 * @author C.Wang
 * @CreateTime 2022/4/27 23:37
 */


public class NettyTestClient {
    public static void main(String[] args) {
        NettyClient client = new NettyClient("127.0.0.1", 9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject res = new HelloObject(12, "This id a message");
        System.out.println(res);
    }
}
