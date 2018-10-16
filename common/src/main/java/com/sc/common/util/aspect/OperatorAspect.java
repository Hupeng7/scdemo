package com.sc.common.util.aspect;

import com.alibaba.fastjson.JSONObject;
import com.sc.common.model.enums.OperatorRole;
import com.sc.common.util.aspect.annotation.Operator;
import com.sc.common.util.check.PermissionChecker;
import com.sc.common.util.res.ResultEnum;
import com.sc.common.util.res.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author hp
 * @time 2018/10/16.
 */
@Aspect
@Component
@Slf4j
@Order(2)
public class OperatorAspect {
    @Pointcut("@annotation(com.sc.common.util.aspect.annotation.Operator)")
    public void pointCut() {
    }

    @Around("pointCut() && @annotation(operator)")
    public Object checkRole(ProceedingJoinPoint target, Operator operator) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        ServletInputStream inputStream = null;
        String body = "";
        try {
            inputStream = request.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            body = StreamUtils.copyToString(inputStream, Charset.forName("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        String specialTokenId = jsonObject.getString("specialTokenId");
        if (null == specialTokenId) {
            specialTokenId = request.getParameter("specialTokenId");
        }
        OperatorRole role = operator.role();
        String name = role.name();
        if (OperatorRole.CONSUMER.equals(name)) {
            if (!PermissionChecker.belongToConsumer(Integer.parseInt(specialTokenId))) {
                return ResultUtil.error(null, ResultEnum.NOT_AUTH);
            } else {
                try {
                    return target.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    return ResultUtil.error(null, ResultEnum.ERROR_UNKNOWN);
                }
            }
        } else if (OperatorRole.STAR.equals(name)) {
            if (!PermissionChecker.belongToStar(Integer.parseInt(specialTokenId))) {
                return ResultUtil.error(null, ResultEnum.NOT_AUTH);
            } else {
                try {
                    return target.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    log.info("");
                    return ResultUtil.error(null, ResultEnum.ERROR_UNKNOWN);
                }
            }
        } else {
            throw new RuntimeException("unknown exception");
        }
    }


}
