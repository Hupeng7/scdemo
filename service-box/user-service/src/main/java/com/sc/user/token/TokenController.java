package com.sc.user.token;

import com.sc.common.model.pojo.User;
import com.sc.common.model.pojo.UserStar;
import com.sc.user.service.token.UserTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hp
 * @time 2018/10/17.
 */
@Api(description = "令牌")
@RestController
@RequestMapping("user/token")
public class TokenController {
    @Autowired
    private UserTokenService userTokenService;

    @GetMapping("star")
    @ApiOperation(value = "内部调用查询版主信息")
    public UserStar checkStarByMysql(@RequestParam("uid") Integer uid) {
        return userTokenService.checkStar(uid);
    }

    @GetMapping("consumer")
    @ApiOperation(value = "内部调用查询粉丝信息")
    public User checkConsumerByMysql(@RequestParam("uid") Integer uid) {
        return userTokenService.checkConsumer(uid);
    }

    @GetMapping("getToken/{uid}")
    @ApiOperation(value = "获取粉丝token")
    public String getToken(@PathVariable("uid") Integer uid) {
        return userTokenService.getToken(uid);
    }

    @GetMapping("getStarToken/uid")
    @ApiOperation(value = "获取版主token")
    public String getStarToken(@PathVariable("uid") Integer uid) {
        return userTokenService.getStarToken(uid);
    }

}
