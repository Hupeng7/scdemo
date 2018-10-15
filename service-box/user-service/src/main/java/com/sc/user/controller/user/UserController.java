package com.sc.user.controller.user;

import com.sc.common.util.res.ResultVO;
import com.sc.user.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hp
 * @version 1.0
 * @description: 用户相关
 * @date: 11:34 2018/10/15 0015
 */
@Api(description = "用户信息")
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 根据id获取用户信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "【需要粉丝端token】根据id获取用户信息")
    @GetMapping("/{id}")
    public ResultVO getUser(@PathVariable("id") Integer id) {
        return userService.getUser(id);
    }


}
