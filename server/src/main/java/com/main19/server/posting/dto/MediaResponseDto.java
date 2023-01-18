package com.main19.server.posting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MediaResponseDto {
	private long mediaId;

	private String mediaUrl;
}
