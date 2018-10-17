package com.sc.user.service.code;

import com.sc.common.model.model.SmsLoginOrRegisterParams;

/**
 * @author hp
 * 发送短信 (验证码等)
 * @time 2018/10/17.
 */
public interface CodeHandler {

    /**
     * 发送短信的接口
     *
     * @param itucode
     * @param phone
     * @return
     */
    String send(String itucode, String phone);

    /**
     * 短信验证码验证
     *
     * @param key
     * @param code
     * @return true 成功，false 失败
     */
    Boolean check(String key, Integer code);

    /**
     * 上一个方法的具象化
     *
     * @param smsLoginOrRegisterParams
     * @return
     */
    Boolean check(SmsLoginOrRegisterParams smsLoginOrRegisterParams);

    /**
     * 发送短信是验证手机号是否被注册过
     *
     * @param itucode
     * @param phone
     * @return
     */
    Boolean check(String itucode, String phone);

}
