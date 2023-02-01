package com.main19.server.domain.chat.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {

        @Positive
        private long chatRoomId;
        @Positive
        private long receiverId;
        @Positive
        private long senderId;
        @NotBlank
        private String chat;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private long chatRoomId;
        private long receiverId;
        private long senderId;
        private String chat;
        private LocalDateTime createdAt;

    }

}
