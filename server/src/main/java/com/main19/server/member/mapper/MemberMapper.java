package com.main19.server.member.mapper;

import com.main19.server.follow.dto.FollowDto;
import com.main19.server.follow.entity.Follow;
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

	@Mapping(source = "followerId.memberId", target = "followingId")
	FollowDto.FollowingResponse followToFollowingResponseDto(Follow follow);

	@Mapping(source = "followingId.memberId", target = "followerId")
	FollowDto.FollowerResponse followToFollowedResponseDto(Follow follow);
}
