package com.main19.server.domain.posting.like.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostingLikeResponseDto {
	private long postingLikeId;
	private long postingId;
	private long memberId;
}
