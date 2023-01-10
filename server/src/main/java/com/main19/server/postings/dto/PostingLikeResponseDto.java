package com.main19.server.postings.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostingLikeResponseDto {
	private Long postingLikeId;
	private long postingId;
	// private Member member;
}
