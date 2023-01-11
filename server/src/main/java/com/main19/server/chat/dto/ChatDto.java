package com.main19.server.chat.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ChatDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        private long receiverId;
        private long senderId;
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
