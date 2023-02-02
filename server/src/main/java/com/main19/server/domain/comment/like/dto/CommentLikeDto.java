package com.main19.server.domain.comment.like.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class CommentLikeDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {

        @Positive
        private long memberId;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private long commentLikeId;
        private long memberId;
        private long commentId;

    }
}
