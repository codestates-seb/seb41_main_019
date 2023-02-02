package com.main19.server.domain.follow.entity;

import com.main19.server.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "follower_member_id")
    private Member followerId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "following_member_id")
    private Member followingId;

    public long getFollowingMemberId() {
        return followingId.getMemberId();
    }

    public long getFollowerMemberId() {
        return followerId.getMemberId();
    }
}
