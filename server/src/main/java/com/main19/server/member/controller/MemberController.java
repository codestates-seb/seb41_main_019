package com.main19.server.member.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.http.RequestEntity;
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
    public RequestEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) {
        return null;
    }

    @PatchMapping("/{member-id}")
    public RequestEntity patchMember(@PathVariable("member-id") @Positive Long memberId,
                                     @Valid @RequestBody MemberDto.Patch requestBody) {
        return null;
    }

	@GetMapping("/{member-id}")
    public RequestEntity getMember(@PathVariable("member-id") @Positive Long memberId) {
		return null;
	}

	@DeleteMapping("{member-id}")
	public RequestEntity deleteMember(@PathVariable("member-id") @Positive Long memberId,
									  @RequestBody MemberDto.Delete requestBody) {
        return null;
    }
}
