package com.wang.rpc.transport.socket.server;

import com.wang.rpc.handler.RequestHandler;
import com.wang.rpc.hook.ShutdownHook;
import com.wang.rpc.provider.ServiceProvider;
import com.wang.rpc.registry.NacosServiceRegistry;
import com.wang.rpc.transport.AbstractRpcServer;
import com.wang.rpc.transport.RpcServer;
import com.wang.rpc.enumeration.RpcError;
import com.wang.rpc.exception.RpcException;
import com.wang.rpc.provider.ServiceProviderImpl;
import com.wang.rpc.registry.ServiceRegistry;
import com.wang.rpc.serializer.CommonSerializer;
import com.wang.rpc.factory.ThreadPoolFactory;
import com.wang.rpc.transport.socket.client.SocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * 远程方法调用的提供者(服务端)
 * <p>
 * 服务端采用反射调用，使用一个ServerSocket监听某个端口，
 * 循环接收连接请求，如果发来了请求就创建一个线程，在新线程中处理调用，这里创建线程采用的是线程池。
 *
 * @author C.Wang
 * @CreateTime 2022/4/24 21:21
 */


public class SocketServer extends AbstractRpcServer {
    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private final ExecutorService threadPool;
    private final CommonSerializer serializer;
    private final RequestHandler requestHandler = new RequestHandler();

    /**
     * RpcService只需要负责启动即可
     *
     * @param
     */
    public SocketServer(String host, int port) {
        this(host, port, DEFAULT_SERIALIZER);
    }

    public SocketServer(String host, int port, Integer serializer) {
        this.host = host;
        this.port = port;
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        this.serviceRegistry = new NacosServiceRegistry();
        this.serviceProvider = new ServiceProviderImpl();
        this.serializer = CommonSerializer.getByCode(serializer);
        scanServices();
    }


    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(host, port));
            logger.info("服务器启动……");
            ShutdownHook.getShutdownHook().addClearAllHook();
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new SocketRequestHandlerThread(socket, requestHandler, serviceRegistry, serializer));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("服务器启动时有错误发生:", e);
        }
    }


}
