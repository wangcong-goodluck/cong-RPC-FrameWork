package com.wang.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  字节流中标识序列化和反序列化器
 * @author C.Wang
 * @CreateTime 2022/4/27 22:24
 */

@AllArgsConstructor
@Getter
public enum SerializerCode {

    KRYO(0),
    JSON(1),
    HESSIAN(2),
    PROTOBUF(3);


    private final int code;
}
