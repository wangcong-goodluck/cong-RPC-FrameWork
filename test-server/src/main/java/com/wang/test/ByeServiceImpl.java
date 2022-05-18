package com.wang.test;

import com.wang.rpc.annotation.Service;
import com.wang.rpc.api.ByeService;

/**
 * @author C.Wang
 * @CreateTime 2022/5/18 22:17
 */

@Service
public class ByeServiceImpl implements ByeService {
    @Override
    public String bye(String name) {
        return "byte," + name;
    }
}
