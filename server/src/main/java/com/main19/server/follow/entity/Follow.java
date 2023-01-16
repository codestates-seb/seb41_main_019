package com.main19.server.follow.entity;

import com.main19.server.member.entity.Member;
import lombok.AllArgsConstructor;
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
    @JoinColumn(name = "followed_member_id")
    private Member followedId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "following_member_id")
    private Member followingId;
}
