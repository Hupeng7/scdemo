package com.sc.user.service.token;

import com.sc.common.model.pojo.User;
import com.sc.common.model.pojo.UserStar;
import com.sc.user.mapper.UserMapper;
import com.sc.user.mapper.UserStarMapper;
import com.sc.user.utils.jwt.JwtProperties;
import com.sc.user.utils.jwt.TokenBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hp
 * @time 2018/10/17.
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class UserTokenService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserStarMapper userStarMapper;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private TokenBuilder tokenBuilder;

    public UserStar checkStar(Integer uid) {
        UserStar userStar = new UserStar();
        userStar.setId(uid);
        return userStarMapper.selectOne(userStar);
    }

    public User checkConsumer(Integer uid) {
        User user = new User();
        user.setId(uid);
        return userMapper.selectOne(user);
    }

    public String getToken(Integer uid) {
        User user = new User();
        user.setId(uid);
        User result = userMapper.selectOne(user);
        String token = tokenBuilder.createToken(result);
        return token;
    }

    public String getStarToken(Integer uid) {
        UserStar userStar = new UserStar();
        userStar.setId(uid);
        UserStar result = userStarMapper.selectOne(userStar);
        String token = tokenBuilder.createToken(result);
        return token;
    }
}
