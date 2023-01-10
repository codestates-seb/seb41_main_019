package com.main19.server.postings.dto;

import com.main19.server.postings.entity.Posting;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostingLikeDto {
	private long postingId;
	private long memberId;
}
