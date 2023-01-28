package com.main19.server.member.entity;

import com.main19.server.chatroom.entity.ChatRoom;
import com.main19.server.myplants.entity.MyPlants;
import javax.persistence.*;

import com.main19.server.follow.entity.Follow;
import com.main19.server.posting.entity.Posting;
import com.main19.server.posting.scrap.entity.Scrap;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
  
	@OneToMany(mappedBy = "member", cascade = REMOVE)
	private List<Posting> postings = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = REMOVE)
	private List<Scrap> scrapPostingList = new ArrayList<>();

	@OneToMany(mappedBy = "followerId", cascade = REMOVE)
	private List<Follow> followerList = new ArrayList<>();

	@OneToMany(mappedBy = "followingId", cascade = REMOVE)
	private List<Follow> followingList = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = REMOVE)
	private List<MyPlants> myPlantsList = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = REMOVE)
	private List<ChatRoom> chatRoomList = new ArrayList<>();

	@PrePersist
	public void prePersist() {
		if (this.location == null) {
			this.location = "...";
		}

		if (this.profileImage == null) {
			this.profileImage = "https://main19-bucket.s3.ap-northeast-2.amazonaws.com/member/profileImage/fcb8c543-bae8-451a-a8e7-6467e2ef7f0c.PNG";
		}

		if (this.profileText == null) {
			this.profileText = "...";
		}
	}
}
