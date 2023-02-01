package com.main19.server.domain.comment.dto;

import com.main19.server.domain.comment.like.dto.CommentLikeGetResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CommentDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {

        @Positive
        private long memberId;
        @NotBlank
        private String comment;

        public Post(String comment) {
            this.comment = comment;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch{

        @Setter
        private long commentId;
        @NotBlank
        private String comment;

    }

    @Getter
    @AllArgsConstructor
    public static class Response{

        private long commentId;
        private long postingId;
        private long memberId;
        private String userName;
        private String profileImage;
        private String comment;
        private long likeCount;
        private List<CommentLikeGetResponseDto> commentLikeList;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

    }
}
