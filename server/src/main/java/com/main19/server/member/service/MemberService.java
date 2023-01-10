package com.main19.server.member.service;

import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.member.entity.Member;
import com.main19.server.member.repository.MemberRepository;
import com.main19.server.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public Member createMember(Member member) {
        verifiedByEmail(member.getEmail());
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.encodePassword(encodedPassword);
        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    public Member updateMember(Member member) {
        // todo 토큰 정보 확인해서 권한 검증후 수정 해야함

        Member findMember = findVerifiedMember(member.getMemberId());
        Member updateMember = Member.builder()
                .memberId(member.getMemberId())
                .profileImage(member.getProfileImage())
                .profileText(member.getProfileText())
                .userName(member.getUserName())
                .location(member.getLocation())

                .email(findMember.getEmail())
                .build();

        return memberRepository.save(updateMember);
    }

    @Transactional(readOnly = true)
    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public void deleteMember(long memberId){
        // todo 토큰 정보 확인해서 권한 검증후 삭제 해야함

        memberRepository.deleteById(memberId);
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
