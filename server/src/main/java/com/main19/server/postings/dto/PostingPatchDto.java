package com.main19.server.postings.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class PostingPatchDto {
	private Long postingId;
	private String postingContent;
	private List<String> tagName;
	public void setPostingId(long postingId) {
		this.postingId = postingId;
	}
}
