package com.main19.server.domain.posting.scrap.entity;

import com.main19.server.domain.member.entity.Member;
import com.main19.server.domain.posting.entity.Posting;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Scrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scrapId;

    @ManyToOne
    @JoinColumn(name = "postingId")
    private Posting posting;
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    public void setPosting(Posting posting) {
        this.posting = posting;
        if (!this.posting.getScrapMemberList().contains(this)) {
            this.posting.getScrapMemberList().add(this);
        }
    }

    public void setMember(Member member) {
        this.member = member;
        if (!this.member.getScrapPostingList().contains(this)) {
            this.member.getScrapPostingList().add(this);
        }
    }

    public long getMemberId() {
        return member.getMemberId();
    }
}
