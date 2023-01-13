package com.main19.server.member.mapper;

import com.main19.server.posting.scrap.dto.ScrapResponseDto;
import com.main19.server.posting.scrap.entity.Scrap;
import org.mapstruct.Mapper;

import com.main19.server.member.dto.MemberDto;
import com.main19.server.member.entity.Member;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	Member memberPostToMember(MemberDto.Post requestBody);
	Member memberPatchToMember(MemberDto.Patch requestBody);
	MemberDto.Response memberToMemberResponse(Member member);

	@Mapping(source = "posting.postingId", target = "postingId")
	@Mapping(source = "member.memberId", target = "memberId")
	ScrapResponseDto scrapToScrapResponseDto(Scrap scrap);
}