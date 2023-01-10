package com.main19.server.postings.tags.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TagResponseDto {
	private Long tagId;
	private String tagName;
}
