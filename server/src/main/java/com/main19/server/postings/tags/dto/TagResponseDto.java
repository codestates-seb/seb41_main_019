package com.main19.server.postings.tags.dto;

import com.main19.server.postings.tags.entity.Tag;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class TagResponseDto {
	private Long tagId;
	private String tagName;
}
