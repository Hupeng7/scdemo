package com.sc.common.util.aspect.annotation;

import com.sc.common.model.enums.OperatorRole;

import java.lang.annotation.*;

/**
 * @author hp
 * @time 2018/10/16.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Operator {
    OperatorRole role();
}
