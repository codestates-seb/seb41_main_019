package com.main19.server.sse.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import com.main19.server.member.entity.Member;
import javax.persistence.Embedded;
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
    private String content;

    @Column(nullable = false)
    private boolean isRead;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SseType sseType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member receiver;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum SseType {
        message,comment,like
    }

    @Builder
    public Sse(Member receiver, SseType sseType, String content, Boolean isRead) {
        this.receiver = receiver;
        this.sseType = sseType;
        this.content = content;
        this.isRead = isRead;
    }
}
