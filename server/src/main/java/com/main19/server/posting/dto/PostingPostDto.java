package com.main19.server.posting.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostingPostDto {
	private long memberId;
	private String postingContent;
	private List<String> tagName;
}
