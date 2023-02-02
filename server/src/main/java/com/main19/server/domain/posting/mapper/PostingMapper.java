package com.main19.server.domain.posting.mapper;

import java.util.List;

import com.main19.server.domain.comment.dto.CommentDto;
import com.main19.server.domain.comment.entity.Comment;
import com.main19.server.domain.comment.like.dto.CommentLikeGetResponseDto;
import com.main19.server.domain.comment.like.entity.CommentLike;
import com.main19.server.domain.posting.dto.PostingPostDto;
import com.main19.server.domain.posting.scrap.dto.ScrapDto;
import com.main19.server.domain.posting.scrap.dto.ScrapPostResponseDto;
import com.main19.server.domain.posting.scrap.entity.Scrap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.main19.server.domain.posting.like.dto.PostingLikeGetResponseDto;
import com.main19.server.domain.posting.dto.PostingPatchDto;
import com.main19.server.domain.posting.dto.PostingResponseDto;
import com.main19.server.domain.posting.entity.Posting;
import com.main19.server.domain.posting.like.dto.PostingLikeResponseDto;
import com.main19.server.domain.posting.like.entity.PostingLike;
import com.main19.server.domain.posting.tags.dto.PostingTagsResponseDto;
import com.main19.server.domain.posting.tags.entity.PostingTags;
import com.main19.server.domain.posting.tags.entity.Tag;

@Mapper(componentModel = "spring")
public interface PostingMapper {
	Posting postingPostDtoToPosting(PostingPostDto requestBody);
	Posting postingPatchDtoToPosting(PostingPatchDto requestBody);

	@Mapping(source = "member.memberId", target = "memberId")
	@Mapping(source = "member.userName", target = "userName")
	@Mapping(source = "member.profileImage", target = "profileImage")
	PostingResponseDto postingToPostingResponseDto(Posting posting);

	List<PostingResponseDto> postingsToPostingsResponseDto(List<Posting> postings);

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

	@Mapping(source = "member.userName" , target = "userName")
	@Mapping(source = "member.memberId" , target = "memberId")
	@Mapping(source = "member.profileImage" , target = "profileImage")
	@Mapping(source = "posting.postingId", target = "postingId")
	CommentDto.Response commentsToCommentsResponseDto(Comment comment);

	@Mapping(source = "member.memberId" , target = "memberId")
	@Mapping(source = "member.userName" , target = "userName")
	CommentLikeGetResponseDto commentLikeToCommentLikeResponseGetResponseDto(CommentLike commentLike);

	Scrap scrapDtoToScrap(ScrapDto requestBody);

	@Mapping(source = "posting.postingId", target = "postingId")
	@Mapping(source = "member.memberId", target = "memberId")
	ScrapPostResponseDto scrapToScrapPostResponseDto(Scrap scrap);
}
