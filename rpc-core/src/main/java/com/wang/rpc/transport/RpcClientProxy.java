package com.wang.rpc.transport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

import com.wang.rpc.entity.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);

    private final RpcClient client;

    public RpcClientProxy(RpcClient client) {
        this.client = client;
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
        logger.info("调用方法: {}#{}", method.getDeclaringClass().getName(), method.getName());
        RpcRequest rpcRequest = new RpcRequest(UUID.randomUUID().toString(),method.getDeclaringClass().getName(),
                method.getName(), args, method.getParameterTypes());
        return client.sendRequest(rpcRequest);
    }
}
