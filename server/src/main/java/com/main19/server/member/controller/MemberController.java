package com.main19.server.member.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import com.main19.server.auth.jwt.JwtTokenizer;
import com.main19.server.dto.SingleResponseDto;
import com.main19.server.member.entity.Member;
import com.main19.server.redis.RedisDao;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.main19.server.member.dto.MemberDto;
import com.main19.server.member.mapper.MemberMapper;
import com.main19.server.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/members")
public class MemberController {

    private final MemberMapper mapper;
    private final MemberService memberService;
    private final JwtTokenizer jwtTokenizer;

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) {
        Member member = mapper.memberPostToMember(requestBody);
        return new ResponseEntity(
            new SingleResponseDto(
                mapper.memberToMemberResponse(memberService.createMember(member))),
            HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@RequestHeader(name = "Authorization") String token,
        @PathVariable("member-id") @Positive long memberId,
        @Valid @RequestBody MemberDto.Patch requestBody) {
        requestBody.setMemberId(memberId);

        Member member = memberService.updateMember(mapper.memberPatchToMember(requestBody),token);

        return new ResponseEntity(
            new SingleResponseDto(mapper.memberToMemberResponse(member)), HttpStatus.OK
        );
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId) {
        Member member = memberService.findMember(memberId);
        return new ResponseEntity(
            new SingleResponseDto(
                mapper.memberToMemberResponse(member)), HttpStatus.OK);
    }

    @DeleteMapping("{member-id}")
    public ResponseEntity deleteMember(@RequestHeader(name = "Authorization") String token,
        @PathVariable("member-id") @Positive long memberId) {
        memberService.deleteMember(memberId,token);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // todo logout 엔드포인트 협의
    @PostMapping("/reissue")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
        // token에서 memberId를 가져온다
        // token의 memberId로 member를 찾는다.
        long memberId = jwtTokenizer.getMemberId(token);
        Member findMember = memberService.findMember(memberId);

        // redis에서 member email로 Refresh 토큰을 찾는다
        // Access토큰이 없으면 권한없음 예외 던지기
        String refreshToken = jwtTokenizer.findRefreshToken(findMember);

        // accesstoken 재발행
        String reissuedAtk = jwtTokenizer.reissueAtk(findMember);
        // header에 재발행 access 토큰과 refresh 토큰 태워서 보내기
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + reissuedAtk);
        headers.add("Refresh", refreshToken);

        return ResponseEntity.ok().headers(headers).build();
    }
}
