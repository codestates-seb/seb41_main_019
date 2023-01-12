package com.main19.server.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ChatRoomDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        private long receiverId;
        private long senderId;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private long chatRoomId;
        private long receiverId;
        private long senderId;
    }
}
