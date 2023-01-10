package com.main19.server.postings.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.main19.server.postings.tags.dto.PostingTagsResponseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostingResponseDto {
	private Long postingId;
	private String postingContent;
	private List<MediaResponseDto> postingMedias;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	// private String userName;
	// private String profileImage;
	private List<PostingTagsResponseDto> tags;
	private long likeCount;
	private List<PostingLikeResponseDto> postingLikes;
}
