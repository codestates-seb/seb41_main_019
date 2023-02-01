package com.main19.server.global.config;

import com.main19.server.global.auth.filter.JwtAuthenticationFilter;
import com.main19.server.global.auth.filter.JwtVerificationFilter;
import com.main19.server.global.auth.handler.MemberAccessDeniedHandler;
import com.main19.server.global.auth.handler.MemberAuthenticationEntryPoint;
import com.main19.server.global.auth.handler.MemberAuthenticationFailureHandler;
import com.main19.server.global.auth.handler.MemberAuthenticationSuccessHandler;
import com.main19.server.global.auth.jwt.JwtTokenizer;
import com.main19.server.global.auth.utils.CustomAuthorityUtils;
import com.main19.server.global.redis.RedisDao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final RedisDao redisDao;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 생성하지 않도록 설정
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        // TODO Admin 계정 생성시 권한 추가가 필요합니다.
                        .antMatchers(HttpMethod.POST, "/members/sign-up/**").permitAll() // 회원가입
                        .antMatchers(HttpMethod.POST, "/members/reissues/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/members/**").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/members/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/members/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/members/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/posts/**").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/posts/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/posts/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/posts/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/comments/**").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/comments/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/comments/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/comments/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/scrap/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/scrap/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/followings/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/followings/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/followed/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/myplants/").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/myplants/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/myplants/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/myplants/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/notification/**").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/notification/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/notification/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/message/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/chatroom/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/chatroom/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/member").permitAll()
                        .anyRequest().permitAll()
                );
        return http.build();
    }

    // PasswordEncoder Bean 객체 생성 -> 없으면 service 계층에서 에러
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // 구체적인 CORS 정책 생성
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000","https://increaf.com","https://www.increaf.com"));
//        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Refresh");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // JwtAuthenticationFilter를 등록하는 역할
    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception { // configure() 메서드를 오버라이드 해서 Cunfiguration 을 커스터마이징 할 수 있다.
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class); // getSharedObject(AuthenticationManager.class) 를 통해 AuthenticationManager의 객체를 얻을 수 있다.

            JwtAuthenticationFilter jwtAuthenticationFilter =
                    new JwtAuthenticationFilter(authenticationManager, jwtTokenizer, redisDao); // JwtAuthenticationFilter에 사용되는 객체 DI
            jwtAuthenticationFilter.setFilterProcessesUrl("/member");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

            builder.addFilter(jwtAuthenticationFilter) // addFilter() 메서드를 통해 JwtAuthenticationFilter를 Spring Security Filter Chain에 추가
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }
}
