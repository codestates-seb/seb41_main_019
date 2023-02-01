package com.main19.server.domain.posting.scrap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ScrapPostResponseDto {
    private long scrapId;
    private long postingId;
    private long memberId;
}
