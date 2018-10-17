package com.sc.user.mapper;

import com.sc.common.model.pojo.UserStar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author hp
 * @time 2018/10/17.
 */
@Mapper
public interface UserStarMapper extends tk.mybatis.mapper.common.Mapper<UserStar>, MySqlMapper<UserStar> {
    /**
     * 根据id获取明星信息
     * @param uid
     * @return
     */
    @Select("select * from user_star where id=#{id}")
    @ResultType(UserStar.class)
    UserStar getUserStarBrieInfo(Integer uid);
}
