package com.main19.server.posting.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MediaResponseDto {
	private long mediaId;

	private String mediaUrl;
}
