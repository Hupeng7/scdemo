package com.sc.user.controller.user;

import com.sc.common.model.pojo.User;
import com.sc.common.model.pojo.UserStar;
import com.sc.user.utils.jwt.JwtHelper;
import com.sc.user.utils.jwt.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hp
 * @time 2018/10/17.
 */

@RequestMapping()
@RestController
public class TestToken {
    @Autowired
    private JwtProperties jwtProperties;

    @GetMapping("/createToken")
    public String createToken() {

        UserStar userStar = new UserStar();
        userStar.setId(2);
        String starStr = JwtHelper.createJwtForStar(jwtProperties.getValidTime(), jwtProperties.getStarSecret(), userStar);

        User user = new User();
        user.setId(1);
        String consumerStr = JwtHelper.createJwt(jwtProperties.getValidTime(), jwtProperties.getCustomerSecret(), user);
        return "starStr--->" + starStr + "/n" + "consumerStr--->" + consumerStr;
    }


}
