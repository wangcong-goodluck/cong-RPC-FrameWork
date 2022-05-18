package com.wang.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.wang.rpc.loadbalancer.LoadBalancer;
import com.wang.rpc.loadbalancer.RandomLoadBalancer;
import com.wang.rpc.util.NacosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author C.Wang
 * @CreateTime 2022/5/10 21:32
 */


public class NacosServiceDiscovery implements ServiceDiscovery {

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceDiscovery.class);

    private final LoadBalancer loadBalancer;

    /**
     *  负载均衡策略，在创建客户端时指定
     *
     * @param loadBalancer
     */
    public NacosServiceDiscovery(LoadBalancer loadBalancer) {
        if (loadBalancer == null) {
            this.loadBalancer = new RandomLoadBalancer();//无参就用默认策略
        } else {
            this.loadBalancer = loadBalancer;//有参就用传入策略。
        }
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> Instances = NacosUtil.getAllInstance(serviceName);
            Instance instance = Instances.get(0);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            logger.error("获取服务时有错误发生：", e);
        }

        return null;
    }
}
