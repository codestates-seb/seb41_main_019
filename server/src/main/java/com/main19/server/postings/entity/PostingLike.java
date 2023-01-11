package com.main19.server.postings.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.main19.server.postings.entity.Posting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PostingLike {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postingLikeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POSTING_ID")
	private Posting posting;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;


	public void setPosting(Posting posting) {
		this.posting = posting;
		if (!this.posting.getPostingLikes().contains(this)) {
			this.posting.getPostingLikes().add(this);
		}
	}

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "MEMBER_ID")
	// private Member member;
}
