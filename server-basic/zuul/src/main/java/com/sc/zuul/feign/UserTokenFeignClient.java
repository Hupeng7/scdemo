package com.sc.zuul.feign;

import com.sc.common.model.pojo.User;
import com.sc.common.model.pojo.UserStar;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hp
 * @time 2018/10/16.
 */
@Component
@FeignClient(name = "user-service")
public interface UserTokenFeignClient {
    /**
     * 检查明星信息  数据源:mysql
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "user/token/star", method = RequestMethod.GET)
    UserStar checkStarByMysql(@RequestParam("uid") Integer uid);

    /**
     * 检查粉丝信息 数据源: mysql
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "user/token/consumer", method = RequestMethod.GET)
    User checkConsumerByMysql(@RequestParam("uid") Integer uid);


}
