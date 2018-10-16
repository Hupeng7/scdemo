package com.sc.user.utils.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author hp
 * @time 2018/10/16.
 */
@Data
@ConfigurationProperties("token")
@Component
public class JwtProperties {
    private long validTime;
    private long refreshTime;
    private String starSecret;
    private String customerSecret;
}
