package com.wang.rpc.exception;

/**
 * 序列化异常
 *
 * @author C.Wang
 * @CreateTime 2022/4/28 19:54
 */


public class SerializeException extends RuntimeException{

    public SerializeException(String msg) {
        super(msg);
    }
}
