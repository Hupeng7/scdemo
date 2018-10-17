package com.sc.user.random;

import java.util.Random;

/**
 * @author hp
 * 随机生成6位数
 * @time 2018/10/17.
 */
public class RandomCreator {
    private static final int MIN = 100000;

    public static Integer createAuthCode() {
        int code = new Random().nextInt(999999);
        if (code < MIN) {
            code += MIN;
        }
        return code;

        // return (int) (Math.random() * 900000) + 100000;
    }
}
