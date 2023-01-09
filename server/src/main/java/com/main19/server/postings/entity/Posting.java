package com.main19.server.postings.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.main19.server.postings.tags.entity.PostingTags;
import com.main19.server.postings.tags.entity.Tag;

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
	// cloumnDefinition 을 이용하면 원하는 컬럼 타입으로 데이터 추출이 가능

	@OneToMany(mappedBy = "posting", cascade = CascadeType.REMOVE)
	private List<Media> postingMedias = new ArrayList<>();

	private LocalDateTime createdAt;

	private LocalDateTime modifiedAt = LocalDateTime.now();

	// 연관관계 매핑 필요
	// @ManyToOne
	// @JoinColumn(name = "member_id")
	// private Member member;


	// 유저 네임과 프로필이미지 컬럼이 디비 상에 꼭 존재해야 할까?? 리스폰스에만 담기면 되지않나
	// private String userName;
	// private String profileImage;

	// // 연관관계 매핑 필요
	// @OneToMany
	// private List<Comments> comments;

	@OneToMany(mappedBy = "posting")
	private List<PostingTags> tags = new ArrayList<>();

	private long likeCount;


	@OneToMany(mappedBy = "posting", cascade = CascadeType.REMOVE) //외래키의 역할을 하는 필드
	private List<PostingLike> postingLikes = new ArrayList<>();

}
