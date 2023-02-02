package com.main19.server.global.auth.filter;

import com.main19.server.global.auth.jwt.JwtTokenizer;
import com.main19.server.global.auth.utils.CustomAuthorityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
        } catch (SignatureException se) {
            request.setAttribute("exception", se);
        } catch (ExpiredJwtException ee) {
            request.setAttribute("exception", ee);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization"); // Authorization header 의 값을 얻은 후에
        return authorization == null || !authorization.startsWith("Bearer"); // 값이 null이거나 Bearer로 시작하지 않으면 해당 Filter의 동작을 수행하지 않도록 한다.
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ",""); // request Header에서 JWT를 얻는다. 이후 Bearer 부분을 제거한다.
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()); // JWT서명을 검증하기 위한 SecretKey를 얻는다.
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody(); // JWT에서 Claims를 파싱한다.

        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String email = (String) claims.get("email"); // jwt에서 파싱한 claims에서 email을 가져온다.
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List<String>) claims.get("roles")); // Claims에서 얻은 권한 정보로 List<GrantedAuthority>를 생성한다.
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities); // email과 List<GrantedAuthority>를 포함한 Authentication 객체를 생성한다.
        SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContext에 Authentication 객체를 저장한다.
    }
}
