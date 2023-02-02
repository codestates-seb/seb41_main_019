package com.main19.server.global.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.main19.server.global.auth.dto.LoginDto;
import com.main19.server.global.auth.jwt.JwtTokenizer;
import com.main19.server.domain.member.entity.Member;
import com.main19.server.global.redis.RedisDao;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;

// username/password 기반의 인증을 처리하기 위해 UsernamePasswordAuthenticationFilter를 상속받는다
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;
    private final RedisDao redisDao;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        // todo 메서드 내부에서 인증로직 구현

        ObjectMapper objectMapper = new ObjectMapper(); // username과 password를 DTO 클래스로 역직렬화에 필요
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class); // 여기서 ServletInputStream을 LoginDto로 변환
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()); // username과 password를 포함한 AuthenticationToken 생성

        return authenticationManager.authenticate(authenticationToken); // Token을 AuthenticationManager에게 전달
    }

    // 클라이언트의 인증정보를 이용해서 인증에 성공할 경우 호출된다
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        Member member = (Member) authResult.getPrincipal(); // Member 엔티티 클래스의 객체를 얻는다.
        String accessToken = jwtTokenizer.delegateAccessToken(member);
        String refreshToken = jwtTokenizer.delegateRefreshToken(member);

        redisDao.setValues(member.getEmail(), refreshToken, Duration.ofMinutes(jwtTokenizer.getRefreshTokenExpirationMinutes()));

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
