package com.sc.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.sc.common.model.pojo.TokenInfo;
import com.sc.common.model.pojo.User;
import com.sc.common.model.pojo.UserStar;
import com.sc.common.util.json.JsonUtil;
import com.sc.common.util.res.ResultEnum;
import com.sc.common.util.res.ResultUtil;
import com.sc.zuul.feign.UserTokenFeignClient;
import com.sc.zuul.jwt.TokenParser;
import com.sc.zuul.redis.RedisHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static com.sc.common.util.constant.SysConstants.USER_HASH_PREFIX;
import static com.sc.common.util.constant.SysConstants.USER_STAR_HASH_PREFIX;

/**
 * @author hp
 * @time 2018/10/16.
 */
@Slf4j
@Component
@ResponseBody
public class TokenFilter extends ZuulFilter {
    private static final String[] WHITELIST = {"login", "sendauthcode", "register", "loginByOldOpenid", "superLogin", "createToken"};

    @Autowired
    private UserTokenFeignClient userTokenFeignClient;

    @Autowired
    private TokenParser tokenParser;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return Stream.of(WHITELIST)
                .noneMatch(s -> getCurrentContext()
                        .getRequest().getRequestURL().toString().contains(s));
    }

    /**
     * 过滤器核心逻辑
     * 过滤白名单外所有请求
     * 验证是否有token或token是否正确/过期
     * 验证失败，返回401
     * 验证成功，放行
     *
     * @return
     */
    @Override
    public Object run() {
        RequestContext context = getCurrentContext();
        String token = context.getRequest().getParameter("access_token");
        if (!requestLimit(token, context)) {
            setToFastRequstResponse(context);
        }
        if (!checkToken(token, context)) {
            setBadAuthResponse(context);
        }
        return null;
    }

    /**
     * 验证token
     *
     * @param token
     * @param context
     * @return
     */
    private boolean checkToken(String token, RequestContext context) {
        //token为空直接返回false
        if (token == null) {
            return false;
        }

        //解析token
        TokenInfo tokenInfo = tokenParser.parseToken(token);
        if (null != tokenInfo) {
            Integer uid = tokenInfo.getUid();
            if (uid > 0) {
                try {
                    Object mapField = RedisHandler.getMapField(USER_HASH_PREFIX, uid.toString());
                    User user = mapField != null ? JSON.parseObject(mapField.toString(), User.class)
                            : userTokenFeignClient.checkConsumerByMysql(uid);
                    if (null != user) {
                        setSuccessResponse(context, user.getId());
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } else if (uid < 0) {
                try {
                    {
                        Object mapField = RedisHandler.getMapField(USER_STAR_HASH_PREFIX, uid.toString());
                        UserStar userStar = mapField != null ? JSON.parseObject(mapField.toString(), UserStar.class)
                                : userTokenFeignClient.checkStarByMysql(uid);
                        if (null != userStar) {
                            setSuccessResponse(context, userStar.getId());
                            return true;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 设置过滤器成功验证返回值
     *
     * @param context
     * @param id
     * @throws IOException
     */
    private void setSuccessResponse(RequestContext context, Integer id) throws IOException {
        InputStream inputStream = context.getRequest().getInputStream();
        Map<String, List<String>> requestQueryParams = context.getRequestQueryParams();
        requestQueryParams.remove("access_token");
        List<String> requestList = new ArrayList<>();
        requestList.add(id.toString());
        requestQueryParams.put("specialTokenId", requestList);
        String body = StreamUtils.copyToString(inputStream, Charset.forName("utf-8"));
        log.info(body);
        JSONObject jsonObject = JSONObject.parseObject(body);
        if (null == jsonObject || jsonObject.isEmpty()) {
            jsonObject = new JSONObject();
        }
        String newBody = jsonObject.toString();
        final byte[] requestBodyBytes = newBody.getBytes();
        context.setRequest(new HttpServletRequestWrapper(context.getRequest()) {
            @Override
            public int getContentLength() {
                return requestBodyBytes.length;
            }

            @Override
            public long getContentLengthLong() {
                return requestBodyBytes.length;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                return new ServletInputStreamWrapper(requestBodyBytes);
            }
        });
        context.setSendZuulResponse(true);
        context.setResponseStatusCode(200);
        context.set("isSuccess", true);

    }

    /**
     * 设置过滤器错误验证返回值
     *
     * @param context
     */
    private void setBadAuthResponse(RequestContext context) {
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(401);
        HttpServletResponse response = context.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        context.setResponse(response);
        context.setResponseBody(JsonUtil.toJSONString(ResultUtil.error(null, ResultEnum.NOT_AUTH)));
    }

    /**
     * 请求限速
     *
     * @param token
     * @param context
     * @return
     */
    private boolean requestLimit(String token, RequestContext context) {
        if (RedisHandler.get(token) != null) {
            setBadAuthResponse(context);
            return false;
        }
        RedisHandler.set(token, true);
        RedisHandler.setExpireTime(token, 500, TimeUnit.MILLISECONDS);
        return true;
    }

    /**
     * 请求太快返回值
     *
     * @param context
     */
    private void setToFastRequstResponse(RequestContext context) {
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(403);
        HttpServletResponse response = context.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        context.setResponse(response);
        context.setResponseBody(JsonUtil.toJSONString(ResultUtil.error(null, ResultEnum.REQUEST_TO_FAST)));
    }
}
