package com.main19.server.member.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import com.main19.server.dto.SingleResponseDto;
import com.main19.server.member.entity.Member;
import com.main19.server.s3service.S3StorageService;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.main19.server.member.dto.MemberDto;
import com.main19.server.member.mapper.MemberMapper;
import com.main19.server.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/members")
public class MemberController {

    private final MemberMapper mapper;
    private final MemberService memberService;
    private final S3StorageService storageService;

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) {
        Member member = mapper.memberPostToMember(requestBody);
        return new ResponseEntity(
            new SingleResponseDto(
                mapper.memberToMemberResponse(memberService.createMember(member))),
            HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{member-id}")
    public ResponseEntity patchMember(@RequestHeader(name = "Authorization") String token,
                                      @PathVariable("member-id") @Positive long memberId,
                                      @Valid @RequestBody MemberDto.Patch requestBody) {
        requestBody.setMemberId(memberId);

        Member member = memberService.updateMember(mapper.memberPatchToMember(requestBody), token);

        return new ResponseEntity(
            new SingleResponseDto(mapper.memberToMemberResponse(member)), HttpStatus.OK);
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

    @PostMapping(value = "/{member-id}/profileimage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity postProfileImage(@PathVariable("member-id") @Positive long memberId,
                                           @RequestHeader(name = "Authorization") String token,
                                           @RequestPart MultipartFile profileImage) {
        String imagePath = storageService.uploadProfileImage(profileImage);
        return new ResponseEntity(mapper.memberToMemberResponse(memberService.createProfileImage(memberId, imagePath, token)), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{member-id}/profileimage")
    public ResponseEntity deleteProfileImage(@PathVariable("member-id") @Positive long memberId,
                                             @RequestHeader(name = "Authorization") String token) {
        storageService.removeProfileImage(memberId);
        memberService.deleteProfileImage(memberId, token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
