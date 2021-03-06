package com.xiaolong.toothmanager.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Description: jwt配置类
 * @Author xiaolong
 * @Date 2022/1/13 10:50 下午
 */
@Data
@Component
@ConfigurationProperties(prefix = "tooth.jwt")
public class JwtUtils {

    private long expire;
    private String secret;
    private String header;

    // 生成jwt
    public String generateToken(String username) {

        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 * expire);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)// 7天過期
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // 解析jwt
    public Claims getClaimByToken(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // jwt是否过期
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    // check是否异常
    public void checkJwt(Claims claims) {
        if (claims == null) {
            throw new JwtException("Token异常，jwt非法");
        }
        if (this.isTokenExpired(claims)){
            throw new JwtException("Token异常，Token过期");
        }
    }

    public String getUsername(HttpServletRequest request){
        String token = request.getHeader(this.header);
        Claims claimByToken = getClaimByToken(token);
        checkJwt(claimByToken);
        return claimByToken.getSubject();
    }
}
