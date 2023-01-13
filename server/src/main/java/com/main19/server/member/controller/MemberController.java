package com.main19.server.member.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import com.main19.server.auth.jwt.JwtTokenizer;
import com.main19.server.dto.SingleResponseDto;
import com.main19.server.member.entity.Member;
import com.main19.server.redis.RedisDao;
import org.springframework.http.HttpHeaders;
import com.main19.server.s3service.S3StorageService;
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
    private final JwtTokenizer jwtTokenizer;
    private final S3StorageService storageService;

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) {
        Member member = mapper.memberPostToMember(requestBody);
        return new ResponseEntity(
            new SingleResponseDto(
                mapper.memberToMemberResponse(memberService.createMember(member))),
            HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{member-id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity patchMember(@RequestHeader(name = "Authorization") String token,
                                      @PathVariable("member-id") @Positive long memberId,
                                      @Valid @RequestPart MemberDto.Patch requestBody, @RequestPart MultipartFile profileImage)
            throws MultipartException, MissingServletRequestPartException {

        String imagePath = null;
        if (profileImage != null) {
            imagePath = storageService.uploadProfileImage(profileImage);
        }

        requestBody.setMemberId(memberId);

        Member member = memberService.updateMember(mapper.memberPatchToMember(requestBody), imagePath ,token);

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

    @GetMapping("/logouts")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
        long memberId = jwtTokenizer.getMemberId(token);
        String email = memberService.findMember(memberId).getEmail();
        jwtTokenizer.deleteToken(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reissues")
    public ResponseEntity reissueRefreshToken(@RequestHeader("Refresh") String token) {
        long memberId = jwtTokenizer.getMemberId(token);
        Member findMember = memberService.findMember(memberId);
        String reissuedAtk = jwtTokenizer.reissueAtk(findMember);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + reissuedAtk);

        return ResponseEntity.ok().headers(headers).build();
    }
}
