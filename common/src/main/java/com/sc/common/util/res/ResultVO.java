package com.sc.common.util.res;

import lombok.Data;

/**
 * @author hp
 * @version 1.0
 * @description: ${description}
 * @date: 11:06 2018/10/15 0015
 */
@Data
public class ResultVO<T> {
    private Integer code;
    private String msg;
    private T result;
}
