package com.sc.user.mapper;

import com.sc.common.model.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author hp
 * @version 1.0
 * @description: ${description}
 * @date: 11:41 2018/10/15 0015
 */
@Mapper
public interface UserMapper extends tk.mybatis.mapper.common.Mapper<User>, MySqlMapper<User> {
    /**
     * 根据id获取用户信息
     *
     * @param id
     * @return
     */
    @Select("select * from user where id=#{id}")
    @ResultType(User.class)
    User getUser(Integer id);


}
