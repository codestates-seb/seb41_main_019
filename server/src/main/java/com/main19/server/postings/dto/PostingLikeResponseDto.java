package com.main19.server.postings.dto;

import com.main19.server.postings.entity.Posting;
import com.main19.server.postings.mapper.PostingMapper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class PostingLikeResponseDto {
	private long postingLikeId;
	private long postingId;
	private long memberId;
}
