package com.wang.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 *  负载均衡接口
 *
 * @author C.Wang
 * @CreateTime 2022/5/18 20:22
 */


public interface LoadBalancer {
    Instance select(List<Instance> instances);
}
