package com.wang.rpc.transport;

import com.wang.rpc.serializer.CommonSerializer;

/**
 * 服务器类通用接口
 *
 * @author C.Wang
 * @CreateTime 2022/4/27 18:41
 */


public interface RpcServer {
    void start();

    void setSerializer(CommonSerializer serializer);

    //用于向 Nacos 注册服务
    <T> void publishService(Object service, Class<T> serviceClass);
}
