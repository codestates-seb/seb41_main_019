package com.main19.server.domain.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class FollowDto {
    @Getter
    @AllArgsConstructor
    public static class Response {
        private long followId;
        private long followerId;
        private long followingId;
    }

    @Getter
    @AllArgsConstructor
    public static class FollowerResponse {
        private long followId;
        private long followerId;
        private String userName;
        private String profileImage;
        private String profileText;
    }

    @Getter
    @AllArgsConstructor
    public static class FollowingResponse {
        private long followId;
        private long followingId;
        private String userName;
        private String profileImage;
        private String profileText;
    }
}
