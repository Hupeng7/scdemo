package com.sc.user.service.code.chuanglan;

import com.sc.common.model.model.SmsLoginOrRegisterParams;
import com.sc.common.model.model.SmsOpenApiResponse;
import com.sc.common.model.model.SmsSendEntity;
import com.sc.common.model.pojo.UserAuth;
import com.sc.common.util.constant.SysConstants;
import com.sc.common.util.json.JsonUtil;
import com.sc.common.util.res.ResultEnum;
import com.sc.common.util.res.ResultUtil;
import com.sc.common.util.res.ResultVO;
import com.sc.user.mapper.UserAuthMapper;
import com.sc.user.random.RandomCreator;
import com.sc.user.redis.RedisHandler;
import com.sc.user.service.code.CodeHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author hp
 * @time 2018/10/17.
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class ChuanglanSender implements CodeHandler {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    @Autowired
    private RedisHandler redisHandler;

    @Autowired
    private UserAuthMapper userAuthMapper;


    @Override
    public String send(String itucode, String phone) {
        String url = environment.getProperty(SysConstants.SMS_CHUANGLAN_URL);
        HttpHeaders httpHeaders = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/json;charset=UTF-8");
        httpHeaders.setContentType(mediaType);
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
        Integer authCode = RandomCreator.createAuthCode();
        String msg = "验证码为:" + authCode + ",非本人操作请注意";
        SmsSendEntity smsSendEntity = new SmsSendEntity();
        smsSendEntity.setAccount(environment.getProperty(SysConstants.SMS_CHUANGLAN_ACCOUNT));
        smsSendEntity.setPassword(environment.getProperty(SysConstants.SMS_CHUANGLAN_PASSWORD));
        smsSendEntity.setPhone(phone);
        smsSendEntity.setMsg(msg);
        String params = JsonUtil.toJSONString(smsSendEntity);
        HttpEntity<String> formEntity = new HttpEntity<>(params, httpHeaders);
        SmsOpenApiResponse response = restTemplate.postForObject(url, formEntity, SmsOpenApiResponse.class);
        if ("0".equals(response.getCode())) {
            //向redis中存验证码 过期时间为10分钟
            RedisHandler.set(itucode + phone, authCode.toString(),
                    Long.valueOf(environment.getProperty(SysConstants.SMS_CHUANGLAN_CODE_TIMEOUT)));
            RedisHandler.setExpireTime(itucode + phone, 600, TimeUnit.SECONDS);
            return response.getCode();
        }
        return "";
    }

    public ResultVO sendAndCheck(String itucode, String phone) {
        return check(itucode, phone) ? ResultUtil.success(send(itucode, phone))
                : ResultUtil.error(null, ResultEnum.EXIST_PHONE);
    }

    @Override
    public Boolean check(String key, Integer code) {
        int mirror = RedisHandler.get(key) != null ? Integer.parseInt(RedisHandler.get(key).toString())
                : 0;
        if (mirror == code) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean check(SmsLoginOrRegisterParams smsLoginOrRegisterParams) {
        String key = smsLoginOrRegisterParams.getItucode() + smsLoginOrRegisterParams.getPhone();
        return check(key, smsLoginOrRegisterParams.getCode());
    }

    @Override
    public Boolean check(String itucode, String phone) {
        UserAuth userAuth = new UserAuth();
        // 手机号注册类型为1
        userAuth.setIndentityType(1);
        userAuth.setIdentifier(itucode + phone);
        UserAuth result = userAuthMapper.selectOne(userAuth);
        return result == null ? true : false;
    }
}
