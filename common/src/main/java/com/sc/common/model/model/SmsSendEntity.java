package com.sc.common.model.model;

import lombok.Data;

/**
 * @author hp
 * @time 2018/10/17.
 */
@Data
public class SmsSendEntity {
    private String account;

    private String password;

    private String msg;

    private String phone;

}
