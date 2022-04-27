package com.wang.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author C.Wang
 * @CreateTime 2022/4/27 22:36
 */

@AllArgsConstructor
@Getter
public enum PackageType {

    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;

}
