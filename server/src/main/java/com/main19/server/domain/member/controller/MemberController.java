package com.main19.server.domain.member.controller;

import com.main19.server.domain.member.dto.MemberDto;
import com.main19.server.domain.member.entity.Member;
import com.main19.server.domain.member.mapper.MemberMapper;
import com.main19.server.global.dto.MultiResponseDto;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import com.main19.server.global.auth.jwt.JwtTokenizer;
import com.main19.server.global.dto.SingleResponseDto;
import com.main19.server.global.storageService.s3.ProfileStorageService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.main19.server.domain.member.service.MemberService;

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
    private final ProfileStorageService storageService;

    @PostMapping("/sign-up")
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

    @GetMapping("/logouts")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {

        String email = memberService.findTokenMemberEmail(token);
        jwtTokenizer.deleteToken(email);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/reissues")
    public ResponseEntity reissueRefreshToken(@RequestHeader("Refresh") String token) {

        Member findMember = memberService.findTokenMember(token);
        String reissuedAtk = jwtTokenizer.reissueAtk(findMember);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + reissuedAtk);

        return ResponseEntity.ok().headers(headers).build();
    }

    @PostMapping(value = "/{member-id}/profileimage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity postProfileImage(@PathVariable("member-id") @Positive long memberId,
                                           @RequestHeader(name = "Authorization") String token,
                                           @RequestPart MultipartFile profileImage) {
        String imagePath = storageService.uploadProfileImage(profileImage, memberId);
        return new ResponseEntity(mapper.memberToMemberResponse(memberService.createProfileImage(memberId, imagePath, token)), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{member-id}/profileimage")
    public ResponseEntity deleteProfileImage(@PathVariable("member-id") @Positive long memberId,
                                             @RequestHeader(name = "Authorization") String token) {
        storageService.removeProfileImage(memberId);
        memberService.deleteProfileImage(memberId, token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity findMemberName(@RequestParam String search , @RequestParam @Positive int page , @RequestParam @Positive int size) {

        Page<Member> memberPage = memberService.findUserName(search,page-1,size);
        List<Member> memberList = mapper.memberPageToMemberList(memberPage);
        List<MemberDto.Response> response = mapper.memberDtoResponseList(memberList);

        return new ResponseEntity(new MultiResponseDto<>(response,memberPage),HttpStatus.OK);
    }

    @GetMapping("/existences")
    public ResponseEntity isExistMember(@RequestParam String email) {
        boolean isExist = memberService.findMemberEmail(email);

        // 있으면 true, 없으면 false
        return new ResponseEntity(
                new SingleResponseDto(isExist), HttpStatus.OK);
    }
}
