package com.main19.server.posting.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.main19.server.comment.entity.Comment;
import com.main19.server.member.entity.Member;
import com.main19.server.posting.like.entity.PostingLike;
import com.main19.server.posting.scrap.entity.Scrap;
import com.main19.server.posting.tags.entity.PostingTags;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Posting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postingId;

	@Column(columnDefinition = "Text")
	private String postingContent;

	@OneToMany(mappedBy = "posting", cascade = CascadeType.REMOVE)
	private List<Media> postingMedias = new ArrayList<>();

	private LocalDateTime createdAt;

	private LocalDateTime modifiedAt = LocalDateTime.now();

	 @ManyToOne
	 @JoinColumn(name = "member_id")
	 private Member member;

	 @OneToMany(mappedBy = "posting")
	 private List<Comment> comments;

	@OneToMany(mappedBy = "posting")
	private List<PostingTags> tags = new ArrayList<>();

	@OneToMany(mappedBy = "posting")
	private List<Scrap> scrapMemberList = new ArrayList<>();
	private long likeCount;
	private long commentCount;

	@OneToMany(mappedBy = "posting", cascade = CascadeType.REMOVE)
	private List<PostingLike> postingLikes = new ArrayList<>();

	public void createLikeCount() {
		likeCount++;
	}

	public void deleteLikeCount() {
		likeCount--;
	}

	public void createCommentCount() {
		commentCount++;
	}

	public void deleteCommentCount() {
		commentCount--;
	}

	public void setMember(Member member) {
		this.member = member;
		if (!this.member.getPostings().contains(this)) {
			this.member.getPostings().add(this);
		}
	}
}
