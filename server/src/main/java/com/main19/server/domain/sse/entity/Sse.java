package com.main19.server.domain.sse.entity;

import com.main19.server.domain.posting.entity.Posting;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import com.main19.server.domain.member.entity.Member;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sseId;

    @Column(nullable = false)
    private boolean isRead;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SseType sseType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Member receiver;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "posting_id")
    private Posting posting;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum SseType {
        message,comment,postLike,commentLike
    }

    @Builder
    public Sse(Member receiver, SseType sseType, Member sender, Boolean isRead, Posting posting) {
        this.receiver = receiver;
        this.sseType = sseType;
        this.sender = sender;
        this.isRead = isRead;
        this.posting = posting;
    }

    public long getReceiverId() {
        return receiver.getMemberId();
    }
}
