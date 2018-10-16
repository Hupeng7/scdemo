package com.sc.common.util.check;

/**
 * @author hp
 * @time 2018/10/16.
 */
public class PermissionChecker {
    public static Boolean belongToStar(Integer uid) {
        if (uid < 0) {
            return true;
        }
        return false;
    }

    public static Boolean belongToConsumer(Integer uid) {
        if (uid > 0) {
            return true;
        }
        return false;
    }

}
