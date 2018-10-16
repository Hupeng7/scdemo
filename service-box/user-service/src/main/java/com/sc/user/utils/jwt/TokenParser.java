package com.sc.user.utils.jwt;

import com.sc.common.model.pojo.TokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;

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

    private final static String UID = "uid";

    /**
     * 解码token
     *
     * @param jsonWebToken
     * @param secret
     * @return
     */
    public static TokenInfo parstJwt(String jsonWebToken, String secret) {

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                    .parseClaimsJws(jsonWebToken).getBody();
            if (claims.get(UID) != null) {
                TokenInfo tokenInfo = new TokenInfo();
                tokenInfo.setIsToken(1);
                tokenInfo.setUid(Integer.parseInt(claims.get(UID).toString()));
                return tokenInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
