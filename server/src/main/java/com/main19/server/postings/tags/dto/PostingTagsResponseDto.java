package com.main19.server.postings.tags.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.main19.server.postings.entity.Posting;
import com.main19.server.postings.tags.entity.Tag;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class PostingTagsResponseDto {
	private String tagName;
}
