package com.main19.server.domain.follow.service;

import com.main19.server.global.auth.jwt.JwtTokenizer;
import com.main19.server.domain.follow.repository.FollowRepository;
import com.main19.server.global.exception.BusinessLogicException;
import com.main19.server.global.exception.ExceptionCode;
import com.main19.server.domain.follow.entity.Follow;
import com.main19.server.domain.member.entity.Member;
import com.main19.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenizer jwtTokenizer;

    public Follow createFollow(String token, long followedMemberId) {
        long followingMemberId = jwtTokenizer.getMemberId(token);
        if (followedMemberId == followingMemberId) {
            throw new BusinessLogicException(ExceptionCode.SAME_MEMBER);
        }

        verifiedFollow(followingMemberId, followedMemberId);

        Follow follow = new Follow();
        follow.setFollowingId(findFollowMember(followingMemberId));
        follow.setFollowerId(findFollowMember(followedMemberId));

        return followRepository.save(follow);
    }

    public void deleteFollowing(String token, long followId) {
        long memberId = jwtTokenizer.getMemberId(token);
        Optional<Follow> optionalFollow = followRepository.findById(followId);
        Follow follow = optionalFollow.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FOLLOW_NOT_FOUND));

        if (memberId != follow.getFollowingMemberId()) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        followRepository.delete(follow);
    }

    public void deleteFollowed(String token, long followId) {
        long memberId = jwtTokenizer.getMemberId(token);
        Optional<Follow> optionalFollow = followRepository.findById(followId);
        Follow follow = optionalFollow.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FOLLOW_NOT_FOUND));

        if (memberId != follow.getFollowerMemberId()) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        followRepository.delete(follow);
    }

    private void verifiedFollow(long followingMemberId, long followedMemberId) {
        Follow findFollow = followRepository.findFollow(followingMemberId, followedMemberId);
        if (findFollow != null) {
            throw new BusinessLogicException(ExceptionCode.FOLLOW_ALREADY_EXIST);
        }
    }

    private Member findFollowMember(long followId) {
        Optional<Member> optionalFollow = memberRepository.findById(followId);
        Member member = optionalFollow.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return member;
    }

    private Follow findExistFollow(long followingMemberId, long followedMemberId) {
        Optional<Follow> optionalFollow = followRepository.findFollowId(followingMemberId, followedMemberId);
        Follow follow = optionalFollow.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FOLLOW_NOT_FOUND));
        return follow;
    }
}
