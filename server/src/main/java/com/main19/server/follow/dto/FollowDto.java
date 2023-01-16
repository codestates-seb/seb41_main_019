package com.main19.server.follow.dto;

import lombok.Getter;

public class FollowDto {
    @Getter
    public static class Post {
        private long followId;
        private long followingId;
        private long followerId;
    }
}
