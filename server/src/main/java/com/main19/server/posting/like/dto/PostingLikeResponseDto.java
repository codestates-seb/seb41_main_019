package com.main19.server.posting.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostingLikeResponseDto {
	private long postingLikeId;
	private long postingId;
	private long memberId;
}