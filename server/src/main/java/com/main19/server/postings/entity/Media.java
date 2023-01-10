package com.main19.server.postings.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Media {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mediaId;

	private String mediaUrl;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) // posting이 삭제 될 때 이미지도 같이 삭제
	@JoinColumn(name = "posting_id")
	private Posting posting;

	public void setPosting(Posting posting) {
		this.posting = posting;
	}

	public Media(String mediaUrl, Posting posting) {
		this.mediaUrl = mediaUrl;
		this.posting = posting;
	}
}
