package com.main19.server.domain.comment.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentLikeGetResponseDto {

    private long commentLikeId;
    private long memberId;
    private String userName;

}
