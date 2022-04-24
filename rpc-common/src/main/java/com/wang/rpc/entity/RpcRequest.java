package com.wang.rpc.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 服务端需要哪些信息，才能唯一确定服务端需要调用的接口的方法呢？
 *
 * @author C.Wang
 * @CreateTime 2022/4/24 20:18
 */

/**
 * 接口的名字，和方法的名字，但是由于方法重载的缘故，还需要这个方法的所有参数的类型，最后，客户端调用时，
 * 还需要传递参数的实际值，那么服务端知道以上四个条件，就可以找到这个方法并调用了。我们把这四个条件写到一个对象中。
 * 到时候传递时传递这个对象即可，即RpcRequest对象
 */
@Data
@Builder
public class RpcRequest implements Serializable {
    /**
     * 待调用接口名称
     */
    private String interfaceName;

    /**
     * 待调用方法名称
     */
    private String methodName;

    /**
     * 调用方法的参数
     */
    private Object[] parameters;

    /**
     * 调用方法的参数类型
     */
    private Class<?>[] paramTypes;

}
