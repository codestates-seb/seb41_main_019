package com.main19.server.sse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SseResponseDto {

    private long sseId;
    private long memberId;
    private String profileImage;
    private String userName;
    private boolean isRead;
    private String sseType;

}
