package com.main19.server.member.service;

import com.main19.server.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {
    public Member createMember(Member member) {
        return null;
    }

    public Member updateMember(Member member) {
        return null;
    }

    @Transactional(readOnly = true)
    public Member findMember(Member member) {
        return null;
    }

    public void deleteMember(long memberId){

    }
}
