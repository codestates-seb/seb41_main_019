package com.main19.server.posting.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostingPatchDto {
	private long postingId;
	private String postingContent;
	private List<String> tagName;
	public void setPostingId(long postingId) {
		this.postingId = postingId;
	}
}
