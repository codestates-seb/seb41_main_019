package com.main19.server.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class FollowDto {
    @Getter
    @AllArgsConstructor
    public static class Response {
        private long followId;
        private long followedId;
        private long followingId;
    }

    @Getter
    @AllArgsConstructor
    public static class FollowedResponse {
        private long followId;
        private long followedId;
    }

    @Getter
    @AllArgsConstructor
    public static class FollowingResponse {
        private long followId;
        private long followingId;
    }
}
