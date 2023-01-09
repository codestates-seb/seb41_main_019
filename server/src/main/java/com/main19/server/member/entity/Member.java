package com.main19.server.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class Member {
	@Id
	@GeneratedValue
	private Long memberId;

	private String userName;

	private String email;

	private String profileImage;

	@Column(columnDefinition = "TEXT")
	private String profileText;

	private String belong;
}
