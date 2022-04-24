package com.wang.test;

import com.wang.rpc.client.RpcClient;
import com.wang.rpc.server.RpcServer;

/**
 *测试服务提供方(服务器)
 *
 * @author C.Wang
 * @CreateTime 2022/4/24 22:56
 */

/**
 * 服务端侧，因为已经实现了一个HelloService的实现类HelloServiceImpl的实现类，
 * 我们只需要创建一个RpcServer并且把这个实现类注册进去就行。
 */
public class TestServer {
    public static void main(String[] args) {
        HelloServiceImpl helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 9000);
    }
}
