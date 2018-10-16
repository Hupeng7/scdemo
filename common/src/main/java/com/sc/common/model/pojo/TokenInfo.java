package com.sc.common.model.pojo;

import lombok.Data;

/**
 * @author hp
 * @time 2018/10/16.
 */
@Data
public class TokenInfo {
    private Integer uid;

    /**
     * 1.合法 2 非法 3 过期
     */
    private Integer isToken;
}
