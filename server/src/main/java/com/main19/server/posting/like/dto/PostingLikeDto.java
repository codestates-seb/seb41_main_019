package com.main19.server.posting.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostingLikeDto {
	private long postingId;
	private long memberId;
}
