package com.main19.server.follow.controller;

import com.main19.server.auth.jwt.JwtTokenizer;
import com.main19.server.dto.SingleResponseDto;
import com.main19.server.follow.entity.Follow;
import com.main19.server.follow.mapper.FollowMapper;
import com.main19.server.follow.service.FollowService;
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
    private final JwtTokenizer jwtTokenizer;
    private final FollowMapper mapper;
    private final FollowService followService;

    @PostMapping("/followings/{member-id}")
    public ResponseEntity postFollow(@PathVariable("member-id") @Positive long followedMemberId,
                                     @RequestHeader(name = "Authorization") String token) {

        long followingMemberId = jwtTokenizer.getMemberId(token);
        Follow follow = followService.createFollow(followingMemberId, followedMemberId);

        return new ResponseEntity(
                new SingleResponseDto(mapper.followToFollowResponseDto(follow)), HttpStatus.CREATED
        );
    }

    @DeleteMapping("/followings/{follow-id}")
    public ResponseEntity deleteFollowing(@PathVariable("follow-id") @Positive long followId,
                                       @RequestHeader(name = "Authorization") String token) {
        followService.deleteFollowing(token, followId);
        return ResponseEntity.noContent().build();
    }

    // todo 엔드포인트 follower로 수정 -> 팀원들에게 사전에 공유하고 변경하기, security configuration 변경
    @DeleteMapping("/followed/{follow-id}")
    public ResponseEntity deleteFollowed(@PathVariable("follow-id") @Positive long followId,
                                       @RequestHeader(name = "Authorization") String token) {
        followService.deleteFollowed(token, followId);
        return ResponseEntity.noContent().build();
    }
}