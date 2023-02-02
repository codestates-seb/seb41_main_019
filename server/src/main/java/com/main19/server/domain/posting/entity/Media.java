package com.main19.server.domain.posting.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Media {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mediaId;

	private String mediaUrl;

	private String thumbnailUrl;
	private String format;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) // posting이 삭제 될 때 이미지도 같이 삭제
	@JoinColumn(name = "posting_id")
	private Posting posting;
}
