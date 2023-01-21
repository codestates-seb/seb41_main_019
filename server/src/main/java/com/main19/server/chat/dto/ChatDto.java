package com.main19.server.chat.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ChatDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

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

        private long receiverId;
        private long senderId;
        private String chat;
        private LocalDateTime createdAt;
    }

}
