package com.wang.rpc;

import com.wang.rpc.entity.RpcRequest;

/**
 * 客户端类通用接口
 *
 * @author C.Wang
 * @CreateTime 2022/4/27 18:42
 */


public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
}