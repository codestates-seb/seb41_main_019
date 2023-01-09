package com.main19.server.postings.dto;

import com.main19.server.postings.entity.Posting;
import com.main19.server.postings.mapper.PostingMapper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class PostingLikeResponseDto {
	private Long postingLikeId;
	private long postingId;
	// private Member member;
}
