package com.main19.server.domain.sse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SseResponseDto {

    private long sseId;
    private long memberId;
    private long postingId;
    private String profileImage;
    private String userName;
    private boolean isRead;
    private String sseType;

}
