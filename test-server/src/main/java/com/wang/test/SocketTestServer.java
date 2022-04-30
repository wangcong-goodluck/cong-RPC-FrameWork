package com.wang.test;

import com.wang.rpc.registry.DefaultServiceRegistry;
import com.wang.rpc.serializer.HessianSerializer;
import com.wang.rpc.socket.server.SocketServer;

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
public class SocketTestServer {
    public static void main(String[] args) {
        HelloServiceImpl helloService = new HelloServiceImpl();

        //RpcServer rpcServer = new RpcServer();
        //rpcServer.register(helloService, 9000);//注册完helloService后，服务器就自行启动了。一个服务器只能注册一个服务

        DefaultServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.setSerializer(new HessianSerializer());
        socketServer.start(9999);
    }
}
