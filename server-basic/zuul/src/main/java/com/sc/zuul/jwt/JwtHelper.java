package com.sc.zuul.jwt;

import com.sc.common.model.pojo.TokenInfo;
import com.sc.common.model.pojo.User;
import com.sc.common.model.pojo.UserStar;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * @author hp
 * jwt 工具类
 * @time 2018/10/16.
 */
public class JwtHelper {

    private final static String UID = "uid";

    /**
     * 版主端创建token 方式
     *
     * @param validTime
     * @param secret
     * @param userStar
     * @return
     */
    public static String createJwtForStar(long validTime, String secret, UserStar userStar) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成jwt的参数
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "jwt")
                .claim("uid", -userStar.getId())
                .signWith(signatureAlgorithm, signingKey);
        // 添加token过期时间
        if (validTime >= 0) {
            long expMillis = nowMillis + validTime;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }
        //生成jwt
        return "star" + builder.compact();
    }

    /**
     * 粉丝端创建token方法
     *
     * @param validTime
     * @param secret
     * @param user
     * @return
     */
    public static String createJwt(long validTime, String secret, User user) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        // 添加构成jwt的参数
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "jwt")
                .claim("uid", user.getId())
                .signWith(signatureAlgorithm, signingKey);
        //添加token过期时间
        if (validTime >= 0) {
            long expMillis = nowMillis + validTime;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }
        //生成jwt
        return "consumer" + builder.compact();
    }

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
