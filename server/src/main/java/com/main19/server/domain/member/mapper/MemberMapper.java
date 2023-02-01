package com.main19.server.domain.member.mapper;

import com.main19.server.domain.follow.dto.FollowDto;
import com.main19.server.domain.follow.entity.Follow;
import com.main19.server.domain.posting.scrap.dto.ScrapResponseDto;
import com.main19.server.domain.posting.scrap.entity.Scrap;
import java.util.List;
import org.mapstruct.Mapper;

import com.main19.server.domain.member.dto.MemberDto;
import com.main19.server.domain.member.entity.Member;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	Member memberPostToMember(MemberDto.Post requestBody);
	Member memberPatchToMember(MemberDto.Patch requestBody);
	MemberDto.Response memberToMemberResponse(Member member);
	List<Member> memberPageToMemberList(Page<Member> member);
	List<MemberDto.Response> memberDtoResponseList(List<Member> member);


	@Mapping(source = "posting.postingId", target = "postingId")
	@Mapping(source = "posting.member.userName", target = "userName")
	@Mapping(source = "posting.member.profileImage", target = "profileImage")
	@Mapping(source = "posting.postingContent", target = "postingContent")
	@Mapping(source = "posting.postingMedias", target = "postingMedias")
	ScrapResponseDto scrapToScrapResponseDto(Scrap scrap);

	@Mapping(source = "followerId.memberId", target = "followingId")
	@Mapping(source = "followerId.userName", target = "userName")
	@Mapping(source = "followerId.profileImage", target = "profileImage")
	@Mapping(source = "followerId.profileText", target = "profileText")
	FollowDto.FollowingResponse followToFollowingResponseDto(Follow follow);

	@Mapping(source = "followingId.memberId", target = "followerId")
	@Mapping(source = "followingId.userName", target = "userName")
	@Mapping(source = "followingId.profileImage", target = "profileImage")
	@Mapping(source = "followingId.profileText", target = "profileText")
	FollowDto.FollowerResponse followToFollowedResponseDto(Follow follow);
}
