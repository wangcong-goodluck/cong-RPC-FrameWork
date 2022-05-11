package com.wang.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.wang.rpc.enumeration.RpcError;
import com.wang.rpc.exception.RpcException;
import com.wang.rpc.util.NacosUtil;
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

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            /**
             *  namingService 提供 registerInstance 方法，可以直接向 Nacos 注册服务。
             */
            NacosUtil.registerService(serviceName, inetSocketAddress);
        } catch (NacosException e) {
            logger.error("注册服务时有错误发生:", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

}
