package com.main19.server.member.entity;

import javax.persistence.*;

import com.main19.server.posting.entity.Posting;
import com.main19.server.posting.scrap.entity.Scrap;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member {
	@Id
	@GeneratedValue
	private Long memberId;

	@Column(nullable = false)
	private String userName;

	@Column(unique = true)
	private String email;

	private String profileImage;

	@Column(columnDefinition = "TEXT")
	private String profileText;

	private String location;

	@Column(nullable = false)
	private String password;

	@ElementCollection(fetch = EAGER)
	private List<String> roles = new ArrayList<>();

	public void encodePassword(String password) {
		this.password = password;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public enum MemberRole {
		ROLE_USER,
		ROLE_ADMIN
	}
  
	@OneToMany(mappedBy = "member")
	private List<Posting> postings = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Scrap> scrapPostingList = new ArrayList<>();

	@Builder
	public Member(Long memberId, String userName, String email, String profileImage, String profileText, String location, String password, List<String> roles) {
		this.memberId = memberId;
		this.userName = userName;
		this.email = email;
		this.profileImage = profileImage;
		this.profileText = profileText;
		this.location = location;
		this.password = password;
		this.roles = roles;
	}
}
