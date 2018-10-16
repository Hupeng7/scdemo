package com.sc.zuul.jwt;

import com.sc.common.model.pojo.User;
import com.sc.common.model.pojo.UserStar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hp
 * 创建一个token
 * @time 2018/10/16.
 */
@Component
public class TokenBuilder {
    @Autowired
    private JwtProperties jwtProperties;

    public String createToken(Object object) {
        if (object instanceof UserStar) {
            UserStar userStar = (UserStar) object;
            return JwtHelper.createJwtForStar(jwtProperties.getValidTime(), jwtProperties.getStarSecret(), userStar);
        } else if (object instanceof User) {
            User user = (User) object;
            return JwtHelper.createJwt(jwtProperties.getValidTime(), jwtProperties.getCustomerSecret(), user);
        } else {
            return "";
        }
    }

}
