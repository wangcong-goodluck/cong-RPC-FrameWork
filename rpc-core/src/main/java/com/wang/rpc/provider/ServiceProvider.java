package com.wang.rpc.provider;

/**
 * 保存和提供服务实例对象
 *
 * @author C.Wang
 * @CreateTime 2022/5/9 0:03
 */


public interface ServiceProvider {

    <T> void addServiceProvider(T service, String serviceName);//注册服务信息
    Object getServiceProvider(String serviceName);//获取服务信息
}
