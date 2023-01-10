package com.main19.server.comment.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class CommentLikeDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {

        private long memberId;
        private long commentId;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private long commentLikeId;
        private long memberId;
        private long commentId;

    }
}
