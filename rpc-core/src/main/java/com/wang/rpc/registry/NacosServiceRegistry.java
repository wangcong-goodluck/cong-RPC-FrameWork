package com.wang.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.wang.rpc.enumeration.RpcError;
import com.wang.rpc.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.rmi.Naming;
import java.util.List;

/**
 * Nacos 服务注册中心
 *
 * @author C.Wang
 * @CreateTime 2022/5/9 0:11
 */


public class NacosServiceRegistry implements ServiceRegistry {

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);

    private static final String SERVER_ADDR = "127.0.0.1:8848";
    private static final NamingService namingService;

    /**
     * 连接 Nacos 的过程写在静态代码块中，在类加载时自动连接。
     */

    static {
        try {
            /**
             *  Nacos的使用很简单，通过 NamingFactory创建 NamingService连接 Nacos
             */
            namingService = NamingFactory.createNamingService(SERVER_ADDR);
        } catch (NacosException e) {
            logger.error("连接到Nacos时有错误发生：", e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            /**
             *  namingService 提供 registerInstance 方法，可以直接向 Nacos 注册服务。
             */
            namingService.registerInstance(serviceName, inetSocketAddress.getHostName(), inetSocketAddress.getPort());
        } catch (NacosException e) {
            logger.error("注册服务时有错误发生:", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            /**
             *  namingService 提供 getAllInstances 方法，可以获得提供某个服务的所有提供者的列表。
             */
            List<Instance> instances = namingService.getAllInstances(serviceName);
            Instance instance = instances.get(0);//这里涉及到负载均衡，先选0
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            logger.error("获取服务时有错误发生:", e);
        }
        return null;
    }
}
