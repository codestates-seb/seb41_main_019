package com.main19.server.posting.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
public class PostingLikeDto {
	@Positive
	private long postingId;
	@Positive
	private long memberId;
}
