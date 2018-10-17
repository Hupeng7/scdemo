package com.sc.common.util.constant;

/**
 * @author hp
 * @version 1.0
 * @description: 系统常量类
 * @date: 14:23 2018/10/15 0015
 */
public interface SysConstants {

    /**
     * 短信相关
     */
    String SMS_CHUANGLAN_ACCOUNT = "sms.chuanglan.account";
    String SMS_CHUANGLAN_PASSWORD = "sms.chuanglan.password";
    String SMS_CHUANGLAN_URL = "sms.chuanglan.url";
    String SMS_CHUANGLAN_CODE_TIMEOUT = "sms.chuanglan.code-time-out";

    String DISTRIBUTED_LOCK_INDENTIFICATION = "order_lock";

    /**
     * user和user_star的Redis 前缀
     */
    String USER_HASH_PREFIX = "users";
    String USER_STAR_HASH_PREFIX = "stars";

}
