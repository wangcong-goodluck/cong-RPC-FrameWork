package com.wang.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import com.wang.rpc.entity.RpcRequest;
import com.wang.rpc.entity.RpcResponse;

/**
 * 客户端方面，由于在客户端这一侧并没有接口的具体实现类，就没有办法直接生成实例对象。
 * 这时，可以通过动态代理的方式生成实例，并调用方法时生成需要的RpcRequest对象并发送给服务端
 *
 * RPC客户端动态代理，采用的是JDK动态代理
 *
 * @author C.Wang
 * @CreateTime 2022/4/24 20:52
 */


public class RpcClientProxy implements InvocationHandler {
    private String host;
    private int port;

    //传递host和post来指明服务端的位置，并使用getProxy()方法来生成代理对象
    public RpcClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    /**
     * InvocationHandler接口需要实现invoke()方法，来指明代理对象的方法被调用时的动作
     *
     * 在这里，我们显然就需要生成一个RpcRequest对象，发送出去，然后返回从服务端接收到的结果即可。
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //使用Builder模式来生成RpcRequest对象
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .build();

        //发送的逻辑，使用一个RpcClient对象来实现，这个对象的作用，就是将一个对象发送过去，并接受返回的对象
        RpcClient rpcClient = new RpcClient();
        return ((RpcResponse) rpcClient.sendRequest(rpcRequest, host, port)).getData();
    }
}
