package com.sc.common.model.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author hp
 * @time 2018/10/17.
 */
@Data
public class LoginBaseParams {
    @NotBlank(message = "手机型号不能为空")
    private String phoneType;

    @NotNull(message = "手机系统类型不能为空")
    private Integer phoneModel;

    @NotNull(message = "推送注册标识不能为空")
    @NotBlank(message = "推送注册标识不能为空")
    private String register;

    @NotNull(message = "推送注册类型不能为空")
    private Integer registerType;

}
