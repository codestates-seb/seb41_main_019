package com.main19.server.domain.posting.tags.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.main19.server.domain.posting.entity.Posting;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PostingTags {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postingTagId;

	@ManyToOne
	@JoinColumn(name = "postingId")
	private Posting posting;

	@ManyToOne
	@JoinColumn(name = "tagId")
	private Tag tag;

	public void setPosting(Posting posting) {
		this.posting = posting;
		if (!this.posting.getTags().contains(this)) {
			this.posting.getTags().add(this);
		}
	}

	public void setTag(Tag tag) {
		this.tag = tag;
		if (!this.tag.getPostings().contains(this)) {
			this.tag.getPostings().add(this);
		}
	}
}
