package com.wang.rpc;

import com.wang.rpc.serializer.CommonSerializer;

/**
 * 服务器类通用接口
 *
 * @author C.Wang
 * @CreateTime 2022/4/27 18:41
 */


public interface RpcServer {
    void start(int port);

    void setSerializer(CommonSerializer serializer);
}
