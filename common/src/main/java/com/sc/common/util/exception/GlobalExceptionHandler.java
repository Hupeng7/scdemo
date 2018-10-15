package com.sc.common.util.exception;

import com.sc.common.util.res.ResultEnum;
import com.sc.common.util.res.ResultUtil;
import com.sc.common.util.res.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @author hp
 * @version 1.0
 * @description: 全局异常处理
 * @date: 14:47 2018/10/15 0015
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 未知的异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResultVO doException(Exception e) {
        e.printStackTrace();
        return ResultUtil.error(e.getLocalizedMessage(), ResultEnum.ERROR_UNKNOWN);
    }

    /**
     * 请求方式异常
     *
     * @param httpRequestMethodNotSupportedException
     * @return
     */
    public ResultVO httpRequestMethodNotSupportedExcetion(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        return ResultUtil.error("请检查请求方式是否正确", ResultEnum.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * 参数非法
     *
     * @param methodArgumentNotValidException
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultVO beanValidation(MethodArgumentNotValidException methodArgumentNotValidException) {
        return ResultUtil.error("非法的参数" + methodArgumentNotValidException
                        .getBindingResult()
                        .getFieldError()
                        .getDefaultMessage(),
                ResultEnum.PARAMS_ERROR);
    }

    /**
     * 校验的异常处理
     *
     * @param constraintViolationException
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultVO constraintViolationException(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
        return ResultUtil.error(constraintViolations.iterator().next().getMessage(), ResultEnum.PARAMS_ERROR);
    }

    /**
     * Servlet 底层自带的校验
     *
     * @param missingServletRequestParameterExcetion
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResultVO beanValidation(MissingServletRequestParameterException missingServletRequestParameterExcetion) {
        return ResultUtil.error("非法的参数" +
                        missingServletRequestParameterExcetion.getParameterName(),
                ResultEnum.PARAMS_ERROR);
    }

}
