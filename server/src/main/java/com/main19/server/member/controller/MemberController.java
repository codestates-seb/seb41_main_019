package com.main19.server.member.controller;

import com.main19.server.auth.Login;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import com.main19.server.auth.jwt.JwtTokenizer;
import com.main19.server.dto.SingleResponseDto;
import com.main19.server.member.entity.Member;
import org.springframework.http.HttpHeaders;
import com.main19.server.s3service.S3StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.main19.server.member.dto.MemberDto;
import com.main19.server.member.mapper.MemberMapper;
import com.main19.server.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/members")
public class MemberController {

    private final MemberMapper mapper;
    private final MemberService memberService;
    private final JwtTokenizer jwtTokenizer;
    private final S3StorageService storageService;

    @PostMapping("/sign-up")
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) {
        Member member = mapper.memberPostToMember(requestBody);
        return new ResponseEntity(
            new SingleResponseDto(
                mapper.memberToMemberResponse(memberService.createMember(member))),
            HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{member-id}")
    public ResponseEntity patchMember(@Login Member tokenMember,
                                      @PathVariable("member-id") @Positive long memberId,
                                      @Valid @RequestBody MemberDto.Patch requestBody) {
        requestBody.setMemberId(memberId);

        Member member = memberService.updateMember(mapper.memberPatchToMember(requestBody), tokenMember);

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

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@Login Member tokenMember, @PathVariable("member-id") @Positive long memberId) {
        System.out.println("controller");
        memberService.deleteMember(memberId,tokenMember);
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

    @PostMapping(value = "/{member-id}/profileimage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity postProfileImage(@RequestHeader("Authorization") String token , @PathVariable("member-id") @Positive long memberId,
                                           @RequestPart MultipartFile profileImage) {

        String imagePath = storageService.uploadProfileImage(profileImage);

        Member member = memberService.createProfileImage(token, memberId, imagePath);

        return new ResponseEntity(mapper.memberToMemberResponse(member), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{member-id}/profileimage")
    public ResponseEntity deleteProfileImage(@PathVariable("member-id") @Positive long memberId, @Login Member tokenMember) {
        storageService.removeProfileImage(memberId);
        memberService.deleteProfileImage(memberId, tokenMember);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity findMemberName(@RequestParam String name) {
        return new ResponseEntity(new SingleResponseDto<>(memberService.findMemberName(name)),HttpStatus.OK);
    }
}
