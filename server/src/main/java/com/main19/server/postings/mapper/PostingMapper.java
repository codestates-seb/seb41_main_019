package com.main19.server.postings.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.main19.server.postings.dto.PostingLikeDto;
import com.main19.server.postings.dto.PostingPatchDto;
import com.main19.server.postings.dto.PostingPostDto;
import com.main19.server.postings.dto.PostingResponseDto;
import com.main19.server.postings.entity.Posting;
import com.main19.server.postings.dto.PostingLikeResponseDto;
import com.main19.server.postings.entity.PostingLike;
import com.main19.server.postings.tags.dto.PostingTagsDto;
import com.main19.server.postings.tags.dto.PostingTagsResponseDto;
import com.main19.server.postings.tags.entity.PostingTags;
import com.main19.server.postings.tags.entity.Tag;

@Mapper(componentModel = "spring")
public interface PostingMapper {
	Posting postingPostDtoToPosting(PostingPostDto requestBody);
	Posting postingPatchDtoToPosting(PostingPatchDto requestBody);

	PostingResponseDto postingToPostingResponseDto(Posting posting);

	List<PostingResponseDto> postingsToPostingsResponseDto(List<Posting> postings);


	// 좋아요 매퍼
	PostingLike postingLikeDtoToPostingLike(PostingLikeDto requestBody);

	@Mapping(source = "posting.postingId", target = "postingId")
	@Mapping(source = "member.memberId", target = "memberId")
	PostingLikeResponseDto postingLikeToPostingLikeResponseDto(PostingLike postingLike);

	@Mapping(source = "member.memberId", target = "memberId")
	@Mapping(source = "member.userName", target = "userName")
	PostingLikeGetResponseDto postingLikeToPostingLikeGetResponseDto(PostingLike postingLike);

	// 포스팅시 String리스트로 받아오면 tag로 변환해주기 위해
	Tag tagPostDtoToTag(String tagName);

	PostingTags postingPostDtoToPostingTag(PostingPostDto requestBody);
	PostingTags postingPatchDtoToPostingTag(PostingPatchDto requestBody);

	@Mapping(source = "tag.tagName", target = "tagName")
	PostingTagsResponseDto postingTagsToPostingTagsResponseDto(PostingTags postingTags);
}
