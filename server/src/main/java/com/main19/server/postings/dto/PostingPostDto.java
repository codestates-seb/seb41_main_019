package com.main19.server.postings.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class PostingPostDto {
	private long memberId;
	private String postingContent;
	private List<String> tagName;
}
