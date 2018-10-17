package com.sc.zuul.jwt;

import com.sc.common.model.pojo.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hp
 * @time 2018/10/16.
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class TokenParser {
    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private TokenBuilder tokenBuilder;

    /**
     * 解码token
     *
     * @param token
     * @return
     */
    public TokenInfo parseToken(String token) {
        TokenInfo tokenInfo = null;

        if (token.startsWith("star")) {
            tokenInfo = JwtHelper.parstJwt(token.replace("star", ""), jwtProperties.getStarSecret());
        } else if (token.startsWith("consumer")) {
            tokenInfo = JwtHelper.parstJwt(token.replace("consumer", ""), jwtProperties.getCustomerSecret());
        } else {
            throw new RuntimeException("token error");
        }
        return tokenInfo;

    }
}
