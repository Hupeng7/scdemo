package com.sc.common.util.uuid;

import java.util.UUID;

/**
 * @author hp
 * @version 1.0
 * @description: 生成uuid的工厂工具类
 * @date: 17:32 2018/10/11 0011
 */
public class UUIDFactory {
    public static String create() {
        return UUID.randomUUID().toString();
    }
}
