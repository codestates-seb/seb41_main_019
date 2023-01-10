package com.main19.server.member.entity;

import javax.persistence.*;

import com.main19.server.comment.entity.Comment;
import com.main19.server.postings.entity.Posting;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(mappedBy = "member")
	private List<Posting> postings = new ArrayList<>();

	@Builder
	public Member(Long memberId, String userName, String email, String profileImage, String profileText, String location) {
		this.memberId = memberId;
		this.userName = userName;
		this.email = email;
		this.profileImage = profileImage;
		this.profileText = profileText;
		this.location = location;
	}
}
