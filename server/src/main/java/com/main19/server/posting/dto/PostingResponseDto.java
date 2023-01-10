package com.main19.server.posting.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.main19.server.comment.dto.CommentDto;
import com.main19.server.posting.like.dto.PostingLikeGetResponseDto;
import com.main19.server.posting.tags.dto.PostingTagsResponseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostingResponseDto {
	private long postingId;
	private long memberId;
	private String userName;
	private String profileImage;
	private String postingContent;
	private List<MediaResponseDto> postingMedias;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private List<PostingTagsResponseDto> tags;
	private long likeCount;
	private List<PostingLikeGetResponseDto> postingLikes;
	private long commentCount;
	private List<CommentDto.Response> comments;
}
