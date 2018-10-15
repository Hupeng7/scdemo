package com.sc.common.util.exception;

import com.sc.common.util.res.ResultEnum;
import com.sc.common.util.res.ResultUtil;
import com.sc.common.util.res.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hp
 * @version 1.0
 * @description: ${description}
 * @date: 14:33 2018/10/15 0015
 */
@Slf4j
@RestController
public class FinalExceptionHandler implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/error")
    public ResultVO error(HttpServletRequest request, HttpServletResponse response) {
        log.error("错误的请求路径--->{},请求方式--->{},请求参数--->{}",
                request.getRequestURL(), request.getMethod(), request.getQueryString());
        int code = response.getStatus();
        log.error("http状态码--->{}", code);
        switch (code) {
            case HttpServletResponse.SC_METHOD_NOT_ALLOWED:
                return ResultUtil.error("请检查请求类型是否正确", ResultEnum.REQUEST_TYPE_TO_METHOD_NOT_ALLOW);
            case HttpServletResponse.SC_NOT_FOUND:
                return ResultUtil.error("不存在的映射路径", ResultEnum.NOT_FOUND);
            case HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE:
                return ResultUtil.error("错误的请求方式", ResultEnum.UNSUPPORTED_MEDIA_TYPE);
            default:
                return ResultUtil.error(response.getStatus(), "请求失败，请检查请求路径或请求参数");
        }


    }

}
