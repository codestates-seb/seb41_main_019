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

    @DeleteMapping("/followings/{member-id}")
    public ResponseEntity deleteFollowing(@PathVariable("member-id") @Positive long followId,
                                       @RequestHeader(name = "Authorization") String token) {
        // 팔로잉 한 사람이 팔로잉 취소 (내가 팔로우하고 있는 member 삭제)
        // 계정주인: followingId, 삭제할상대: followerId
        // follow 식별자를 받는다
        // 식별자에서 followingId를 찾고 token의 memberId가 맞는지 검증한다.
        // 아니면 예외 던지기

        followService.deleteFollowing(token, followId);
        return ResponseEntity.noContent().build();
    }

    // todo 엔드포인트 follower로 수정 -> 팀원들에게 사전에 공유하고 변경하기, security configuration 변경
    @DeleteMapping("/followed/{member-id}")
    public ResponseEntity deleteFollowed(@PathVariable("member-id") @Positive long followingMemberId,
                                       @RequestHeader(name = "Authorization") String token) {

        // 팔로워 삭제 (나를 팔로우하고 있는 member 삭제)
        // 계정주인: followerId, 삭제할상대: followingId
        // follow 식별자를 받는다
        // follow 식별자에서 followerId 찾고 토큰의 memberId가 맞는지 검증
        // 아니면 예외던지기

        long followedMemberId = jwtTokenizer.getMemberId(token);
        followService.deleteFollowed(followedMemberId, followingMemberId);
        return ResponseEntity.noContent().build();
    }
}