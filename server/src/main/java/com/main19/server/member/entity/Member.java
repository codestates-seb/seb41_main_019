package com.main19.server.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	@Id
	@GeneratedValue
	private Long memberId;

	private String userName;

	@Column(unique = true)
	private String email;

	private String profileImage;

	@Column(columnDefinition = "TEXT")
	private String profileText;

	private String location;

	private String password;

	public void encodePassword(String password) {
		this.password = password;
	}

	@Builder
	public Member(Long memberId, String userName, String email, String profileImage, String profileText, String location, String password) {
		this.memberId = memberId;
		this.userName = userName;
		this.email = email;
		this.profileImage = profileImage;
		this.profileText = profileText;
		this.location = location;
		this.password = password;
	}
}
