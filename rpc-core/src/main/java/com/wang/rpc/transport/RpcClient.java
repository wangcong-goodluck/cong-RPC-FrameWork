package com.wang.rpc.transport;

import com.wang.rpc.entity.RpcRequest;
import com.wang.rpc.serializer.CommonSerializer;

/**
 * 客户端类通用接口
 *
 * @author C.Wang
 * @CreateTime 2022/4/27 18:42
 */


public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);

    void setSerializer(CommonSerializer serializer);
}
