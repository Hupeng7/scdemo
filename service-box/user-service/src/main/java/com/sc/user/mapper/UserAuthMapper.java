package com.sc.user.mapper;

import com.sc.common.model.pojo.UserAuth;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author hp
 * @time 2018/10/17.
 */
@Mapper
public interface UserAuthMapper extends tk.mybatis.mapper.common.Mapper<UserAuth>, MySqlMapper<UserAuth> {


}
