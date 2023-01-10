package com.main19.server.member.entity;

import javax.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

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

	@ElementCollection(fetch = EAGER)
	private List<String> roles = new ArrayList<>();

	public void encodePassword(String password) {
		this.password = password;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
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

	public enum MemberRole {
		ROLE_USER,
		ROLE_ADMIN
	}
}
