package com.main19.server.follow.controller;

import com.main19.server.auth.jwt.JwtTokenizer;
import com.main19.server.dto.SingleResponseDto;
import com.main19.server.follow.dto.FollowDto;
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
@RequestMapping("/follows")
public class FollowController {
    private final JwtTokenizer jwtTokenizer;
    private final FollowMapper mapper;
    private final FollowService followService;

    @PostMapping("/{member-id}")
    public ResponseEntity postFollow(@PathVariable("member-id") @Positive long followedMemberId,
                                     @RequestHeader(name = "Authorization") String token) {

        long followingMemberId = jwtTokenizer.getMemberId(token);
        Follow follow = followService.createFollow(followingMemberId, followedMemberId);

        return new ResponseEntity(
                new SingleResponseDto(mapper.followToFollowResponseDto(follow)), HttpStatus.CREATED
        );
    }

    @DeleteMapping("{member-id}")
    public ResponseEntity deleteFollow(@PathVariable("member-id") @Positive long followedMemberId,
                                       @RequestHeader(name = "Authorization") String token) {
        long followingMemberId = jwtTokenizer.getMemberId(token);
        followService.deleteFollowing(followingMemberId, followedMemberId);
        return ResponseEntity.noContent().build();
    }
}