package com.main19.server.posting.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostingLikeGetResponseDto {
	private long memberId;
	private String userName;
}