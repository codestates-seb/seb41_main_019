package com.main19.server.posting.scrap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ScrapResponseDto {
    private long scrapId;
    private long memberId;
    private long postingId;
}
