package com.main19.server.member.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import com.main19.server.dto.SingleResponseDto;
import com.main19.server.member.entity.Member;
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
}