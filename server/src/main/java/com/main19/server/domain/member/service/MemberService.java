package com.main19.server.domain.member.service;

import com.main19.server.global.auth.jwt.JwtTokenizer;
import com.main19.server.global.auth.utils.CustomAuthorityUtils;
import com.main19.server.domain.member.entity.Member;
import com.main19.server.domain.member.repository.MemberRepository;
import com.main19.server.global.exception.BusinessLogicException;
import com.main19.server.global.exception.ExceptionCode;
import com.main19.server.global.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Transactional(readOnly = true)
    public Member findTokenMember(String token) {

        long memberId = jwtTokenizer.getMemberId(token);
        return findVerifiedMember(memberId);
    }

    @Transactional(readOnly = true)
    public String findTokenMemberEmail(String token) {
        long memberId = jwtTokenizer.getMemberId(token);
        String email = findVerifiedMember(memberId).getEmail();
        return email;
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
        member.setProfileImage("https://s3.ap-northeast-2.amazonaws.com/main19-bucket/member/profileImage/5ce172e0-35c9-4453-bba2-6b97af732a36.png");
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public boolean findMemberEmail(String search) {
        Member member = memberRepository.checkEmail(search);
        return member != null;
    }

    public Page<Member> findUserName(String search , int page , int size) {
        Page<Member> member = memberRepository.findByUserNameContaining(search, PageRequest.of(page, size, Sort.by("memberId").descending()));
        return member;
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
