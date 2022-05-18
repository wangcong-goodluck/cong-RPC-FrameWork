package com.wang.test;

import com.wang.rpc.annotation.ServiceScan;
import com.wang.rpc.api.HelloService;
import com.wang.rpc.provider.ServiceProviderImpl;
import com.wang.rpc.serializer.CommonSerializer;
import com.wang.rpc.serializer.HessianSerializer;
import com.wang.rpc.transport.RpcServer;
import com.wang.rpc.transport.socket.server.SocketServer;

/**
 *测试服务提供方(服务器)
 *
 * @author C.Wang
 * @CreateTime 2022/4/24 22:56
 */

/**
 * 服务端侧，因为已经实现了一个HelloService的实现类 HelloServiceImpl的实现类，
 * 我们只需要创建一个RpcServer并且把这个实现类注册进去就行。
 */
@ServiceScan
public class SocketTestServer {
    public static void main(String[] args) {
        RpcServer server = new SocketServer("127.0.0.1", 9998, CommonSerializer.HESSIAN_SERIALIZER);
        server.start();
    }
}
