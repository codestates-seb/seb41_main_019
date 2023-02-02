package com.main19.server.domain.posting.scrap.dto;

import com.main19.server.domain.posting.dto.MediaResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ScrapResponseDto {
    private long scrapId;
    private long postingId;
    private String userName;
    private String profileImage;
    private String postingContent;
    private List<MediaResponseDto> postingMedias;
}
