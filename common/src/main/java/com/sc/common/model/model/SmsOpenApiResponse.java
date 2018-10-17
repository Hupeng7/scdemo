package com.sc.common.model.model;

import lombok.Data;

/**
 * @author hp
 * @time 2018/10/17.
 */
@Data
public class SmsOpenApiResponse {
    private String code;

    private String errorMsg;

    private String msgId;

    private String time;

}
