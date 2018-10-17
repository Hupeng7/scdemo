package com.sc.common.model.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author hp
 * @time 2018/10/17.
 */
@Data
public class SmsLoginOrRegisterParams extends LoginBaseParams {

    @NotBlank(message = "区号不能为空")
    private String itucode;

    @NotBlank(message = "手机号不能为空")
    private String phone;

    private Integer code;

    @NotBlank(message = "密码不能为空")
    private String password;

    private Integer type;


}
