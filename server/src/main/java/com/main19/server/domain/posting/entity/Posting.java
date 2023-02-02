package com.main19.server.domain.posting.entity;

import com.main19.server.domain.posting.scrap.entity.Scrap;
import com.main19.server.domain.posting.tags.entity.PostingTags;
import com.main19.server.domain.sse.entity.Sse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.main19.server.domain.comment.entity.Comment;
import com.main19.server.domain.member.entity.Member;
import com.main19.server.domain.posting.like.entity.PostingLike;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

	 @ManyToOne(cascade = CascadeType.PERSIST)
	 @JoinColumn(name = "member_id")
	 private Member member;

	 @OneToMany(mappedBy = "posting", cascade = CascadeType.REMOVE)
	 private List<Comment> comments;

	@OneToMany(mappedBy = "posting", cascade = CascadeType.REMOVE)
	private List<PostingTags> tags = new ArrayList<>();

	@OneToMany(mappedBy = "posting", cascade = CascadeType.REMOVE)
	private List<Scrap> scrapMemberList = new ArrayList<>();
	private long likeCount;
	private long commentCount;

	@OneToMany(mappedBy = "posting", cascade = CascadeType.REMOVE)
	private List<PostingLike> postingLikes = new ArrayList<>();

	@OneToMany(mappedBy = "posting", cascade = CascadeType.REMOVE)
	private List<Sse> sseList = new ArrayList<>();

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

	public long getMemberId() {
		return member.getMemberId();
	}
}
