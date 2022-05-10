package com.wang.test;

import com.wang.rpc.api.HelloObject;
import com.wang.rpc.api.HelloService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 在服务端对HelloService接口进行实现，返回一个字符串
 *
 * @author C.Wang
 * @CreateTime 2022/4/24 20:00
 */


public class HelloServiceImpl implements HelloService{
    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到消息：{}", object.getMessage());
        return "本次处理来自Netty服务";
    }
}
