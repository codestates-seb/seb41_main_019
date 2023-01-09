package com.main19.server.member.mapper;

import org.mapstruct.Mapper;

import com.main19.server.member.dto.MemberDto;
import com.main19.server.member.entity.Member;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	Member memberPostToMember(MemberDto.Post requestBody);
	Member memberPatchToMember(MemberDto.Patch requestBody);
	MemberDto.Response memberToMemberResponse(Member member);
}
