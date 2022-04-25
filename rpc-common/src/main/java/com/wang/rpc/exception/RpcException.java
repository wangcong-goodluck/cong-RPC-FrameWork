package com.wang.rpc.exception;

import com.wang.rpc.enumeration.RpcError;

/**
 * RPC调用异常
 *
 * @author C.Wang
 * @CreateTime 2022/4/25 22:26
 */


public class RpcException extends RuntimeException{
    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }
}
