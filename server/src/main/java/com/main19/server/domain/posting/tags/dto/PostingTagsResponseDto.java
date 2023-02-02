package com.main19.server.domain.posting.tags.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostingTagsResponseDto {
	private String tagName;
}
