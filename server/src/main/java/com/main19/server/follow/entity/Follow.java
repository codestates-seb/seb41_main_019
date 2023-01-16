package com.main19.server.follow.entity;

import com.main19.server.member.entity.Member;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Follow {
    @Id
    @GeneratedValue
    private Long followId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "followed_member_id")
    private Member followedId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "following_member_id")
    private Member followingId;
}
