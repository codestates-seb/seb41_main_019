package com.main19.server.domain.chatroom.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatRoomDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        @Positive
        private long receiverId;
        @Positive
        private long senderId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {

        @Positive
        private long memberId;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private long chatRoomId;
        private long receiverId;
        private long senderId;
        private Long leaveId;

    }
}
