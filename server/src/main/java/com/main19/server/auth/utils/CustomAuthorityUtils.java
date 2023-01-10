package com.main19.server.auth.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthorityUtils {
    @Value("${mail.address.admin}") // yml에서 정의한 관리자 권한을 가질 수 있는 이메일 주소
    private String adminMailAddress;

    // AuthorityUtils 객체로 일반 사용 권한 목록을 생성한다. -> Admin은 admin과 user의 role을 갖는다.
    private final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");

    // AuthorityUtils 객체로 일반 사용 권한 목록을 생성한다. -> user는 user의 role을 갖는다.
    private final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");


    private final List<String> ADMIN_ROLES_STRING = List.of("ADMIN", "USER");
    private final List<String> USER_ROLES_STRING = List.of("USER");


    // 회원가입 시 파라미터로 전달받은 email주소가 yml에서 정의한 admin email과 일치하면 Admin 권한 부여
    // todo 관리자로 등록하기 위한 추가적인 인증 절차가 필요하다.
    public List<GrantedAuthority> createAuthorities(String email) {
        if (email.equals(adminMailAddress )) {
            return ADMIN_ROLES;
        }
        return USER_ROLES;
    }

    public List<String> createRoles(String email) {
        if (email.equals(adminMailAddress)) {
            return ADMIN_ROLES_STRING;
        }
        return USER_ROLES_STRING;
    }

}
