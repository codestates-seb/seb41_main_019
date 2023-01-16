package com.main19.server.follow.service;

import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.follow.entity.Follow;
import com.main19.server.follow.repository.FollowRepository;
import com.main19.server.member.entity.Member;
import com.main19.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public Follow createFollow(long followingMemberId, long followedMemberId) {
        Follow follow = new Follow();
        if (followedMemberId == followingMemberId) {
            throw new BusinessLogicException(ExceptionCode.SAME_MEMBER);
        }
        follow.setFollowedId(findFollow(followedMemberId));
        follow.setFollowingId(findFollow(followingMemberId));
        return followRepository.save(follow);
    }

    public void deleteFollow(long memberId) {
    }


    private Member findFollow(long followId) {
        Optional<Member> optionalFollow = memberRepository.findById(followId);
        Member member = optionalFollow.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return member;
    }
}
