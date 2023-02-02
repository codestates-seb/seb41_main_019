package com.main19.server.domain.posting.tags.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
public class PostingTagsDto {
	@Positive
	private long postingId;
	@Positive
	private long tagId;
}
