package com.main19.server.postings.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.main19.server.postings.tags.dto.PostingTagsResponseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostingResponseDto {
	private long postingId;
	private String postingContent;
	private List<MediaResponseDto> postingMedias;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	// private String userName;
	// private String profileImage;
	// private List<CommentResponseDto> comment;
	private List<PostingTagsResponseDto> tags;
	private long likeCount;
	private List<PostingLikeGetResponseDto> postingLikes;
}
