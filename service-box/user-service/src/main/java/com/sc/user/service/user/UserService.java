package com.sc.user.service.user;

import com.sc.common.model.pojo.User;
import com.sc.common.util.res.ResultUtil;
import com.sc.common.util.res.ResultVO;
import com.sc.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hp
 * @version 1.0
 * @description: ${description}
 * @date: 11:36 2018/10/15 0015
 */
@Slf4j
@Service
@SuppressWarnings(value = "all")
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public ResultVO getUser(Integer id) {
        User user = userMapper.getUser(id);
        return ResultUtil.success(user);
    }
}
