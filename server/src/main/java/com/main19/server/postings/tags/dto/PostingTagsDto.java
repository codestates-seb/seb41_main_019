package com.main19.server.postings.tags.dto;

import com.main19.server.postings.entity.Posting;
import com.main19.server.postings.tags.entity.Tag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostingTagsDto {
	private long postingId;
	private long tagId;
}
