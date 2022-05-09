package com.wang.rpc.entity;

import com.wang.rpc.enumeration.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.net.ResponseCache;

/**
 *
 * RPC 响应实体类
 *
 * 当服务端通过 RpcRequest 中的相关数据调用到目标服务的目标方法后，调用结果就通过 RpcResponse 返回给客户端。
 *
 * @author C.Wang
 * @CreateTime 2022/4/24 20:30
 */

/**
 * 服务器调用完这个方法后，需要给客户端返回哪些信息呢？
 * 如果调用成功的话，显然需要返回值，如果调用失败了，就需要失败的信息，这里封装一个RpcResponse对象。
 */
@Data
@NoArgsConstructor
public class RpcResponse<T> implements Serializable {

    /**
     * 响应状态码
     */
    private Integer statusCode;

    /**
     * 响应状态补充信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应对应的请求号
     */
    private String requestId;


    //快速生成成功与失败的响应对象
    public static <T> RpcResponse<T> success(T data, String requestId) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setRequestId(requestId);
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    public static <T> RpcResponse<T> fail(ResponseCode code, String requestId) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setRequestId(requestId);
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }
}
