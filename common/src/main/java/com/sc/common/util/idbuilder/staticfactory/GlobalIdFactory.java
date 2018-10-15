package com.sc.common.util.idbuilder.staticfactory;

/**
 * @author hp
 * @version 1.0
 * @description: id算法工厂
 * @date: 15:14 2018/10/15 0015
 */
public interface GlobalIdFactory<T> {
    /**
     * 创建一个id
     *
     * @return
     */
    T create();
}
