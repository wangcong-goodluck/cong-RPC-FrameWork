package com.wang.rpc.registry;

/**
 *服务注册表通用接口
 *
 * @author C.Wang
 * @CreateTime 2022/4/25 22:51
 */


public interface ServiceRegistry {
    <T> void register(T service);//注册服务信息
    Object getService(String serviceName);//获取服务信息
}
