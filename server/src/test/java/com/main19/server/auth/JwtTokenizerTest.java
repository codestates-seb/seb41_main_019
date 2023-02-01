package com.main19.server.auth;

import com.main19.server.global.auth.jwt.JwtTokenizer;
import com.main19.server.global.redis.RedisDao;
import io.jsonwebtoken.io.Decoders;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtTokenizerTest {
    private static JwtTokenizer jwtTokenizer;
    private String secretKey;
    private String base64EncodedSecretKey;
    private static RedisDao redisDao;

    @BeforeAll
    public void init() {
        jwtTokenizer = new JwtTokenizer(redisDao);
        secretKey = "thk98k1234thk98k1234thk98k1234thk98k1234thk98k1234";
        base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(secretKey);
    }

    @Test
    public void encodeBase64SecretKeyTest() {
        System.out.println(base64EncodedSecretKey);
        assertThat(secretKey, is(new String(Decoders.BASE64.decode(base64EncodedSecretKey))));
    }

    @Test
    public void generateAccessTokenTest() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", 1);
        claims.put("roles", List.of("USER"));

        String subject = "test access Token";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);
        Date expiration = calendar.getTime();

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
        System.out.println("accessToken = " + accessToken);
        assertThat(accessToken, notNullValue());
    }

    @Test
    public void generateRefreshTokenTest() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", 1);
        claims.put("roles", List.of("USER"));

        String subject = "test refresh token";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);
        Date expiration = calendar.getTime();

        String refreshToken = jwtTokenizer.generateRefreshToken(claims,subject, expiration, base64EncodedSecretKey);
        System.out.println("refreshToken = " + refreshToken);
        assertThat(refreshToken, notNullValue());
    }
}
