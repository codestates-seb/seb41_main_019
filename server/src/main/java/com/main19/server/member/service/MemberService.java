package com.main19.server.member.service;

import com.main19.server.auth.jwt.JwtTokenizer;
import com.main19.server.auth.utils.CustomAuthorityUtils;
import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.member.entity.Member;
import com.main19.server.member.repository.MemberRepository;
import com.main19.server.redis.RedisDao;
import com.main19.server.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    private final CustomBeanUtils<Member> beanUtils;
    private final JwtTokenizer jwtTokenizer;

    public Member createMember(Member member) {
        verifiedByEmail(member.getEmail());
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.encodePassword(encodedPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    public Member updateMember(Member member ,String token) {
        // todo 토큰 정보 확인해서 권한 검증후 수정 해야함
        // todo password 수정할지?

        if (member.getMemberId() != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        Member findMember = findVerifiedMember(member.getMemberId());
        Member updateMember = beanUtils.copyNonNullProperties(member, findMember);

        return memberRepository.save(updateMember);
    }

    @Transactional(readOnly = true)
    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public void deleteMember(long memberId, String token){
        // todo 토큰 정보 확인해서 권한 검증후 삭제 해야함

        if (memberId != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        memberRepository.deleteById(memberId);
    }

    public Member createProfileImage(long memberId, String imagePath, String token) {

        if (memberId != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        Member member = findMember(memberId);
        member.setProfileImage(imagePath);
        return memberRepository.save(member);
    }

    public void deleteProfileImage(long memberId, String token) {

        if (memberId != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        Member member = findMember(memberId);
        member.setProfileImage(null);
        memberRepository.save(member);
    }
    @Transactional(readOnly = true)
    public boolean findMemberName(String search) {
        Member member = memberRepository.findByUserName(search);
        return member != null;
    }

    private void verifiedByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }

    private Member findVerifiedMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }
}
