package com.sc.user.controller.code;

import com.sc.user.service.code.chuanglan.ChuanglanSender;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hp
 * @time 2018/10/17.
 */
@Api(description = "手机验证码发送")
@RestController
@SuppressWarnings("all")
public class CodeSendController {
    @Autowired
    private ChuanglanSender chuanglanSender;



}
