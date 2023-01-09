package com.main19.server.postings.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class MediaResponseDto {
	private long mediaId;

	private String mediaUrl;
}
