package com.main19.server.follow.controller;

import com.main19.server.auth.Login;
import com.main19.server.auth.jwt.JwtTokenizer;
import com.main19.server.dto.SingleResponseDto;
import com.main19.server.follow.dto.FollowDto;
import com.main19.server.follow.entity.Follow;
import com.main19.server.follow.mapper.FollowMapper;
import com.main19.server.follow.service.FollowService;
import com.main19.server.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@Validated
@RequiredArgsConstructor
@RestController
public class FollowController {
    private final FollowMapper mapper;
    private final FollowService followService;

    @PostMapping("/followings/{member-id}")
    public ResponseEntity postFollow(@PathVariable("member-id") @Positive long followedMemberId,
        @Login Member tokenMember) {

        long followingMemberId = tokenMember.getMemberId();
        Follow follow = followService.createFollow(followingMemberId, followedMemberId);

        return new ResponseEntity(
                new SingleResponseDto(mapper.followToFollowResponseDto(follow)), HttpStatus.CREATED
        );
    }

    @DeleteMapping("/followings/{member-id}")
    public ResponseEntity deleteFollowing(@PathVariable("member-id") @Positive long followedMemberId,
        @Login Member tokenMember) {
        long followingMemberId = tokenMember.getMemberId();
        followService.deleteFollowing(followingMemberId, followedMemberId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/followed/{member-id}")
    public ResponseEntity deleteFollowed(@PathVariable("member-id") @Positive long followingMemberId,
        @Login Member tokenMember) {
        long followedMemberId = tokenMember.getMemberId();
        followService.deleteFollowed(followedMemberId, followingMemberId);
        return ResponseEntity.noContent().build();
    }
}