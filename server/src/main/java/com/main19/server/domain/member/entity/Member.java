package com.main19.server.domain.member.entity;

import com.main19.server.domain.chatroom.entity.ChatRoom;
import com.main19.server.domain.comment.entity.Comment;
import com.main19.server.domain.myplants.entity.MyPlants;
import com.main19.server.domain.sse.entity.Sse;
import javax.persistence.*;

import com.main19.server.domain.follow.entity.Follow;
import com.main19.server.domain.posting.entity.Posting;
import com.main19.server.domain.posting.scrap.entity.Scrap;
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

	@Column(nullable = false, unique = true)
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
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = REMOVE)
	private List<Scrap> scrapPostingList = new ArrayList<>();

	@OneToMany(mappedBy = "followerId", cascade = REMOVE)
	private List<Follow> followerList = new ArrayList<>();

	@OneToMany(mappedBy = "followingId", cascade = REMOVE)
	private List<Follow> followingList = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = REMOVE)
	private List<MyPlants> myPlantsList = new ArrayList<>();

	@OneToMany(mappedBy = "receiver", cascade = REMOVE)
	private List<ChatRoom> receiverChatRoomList = new ArrayList<>();

	@OneToMany(mappedBy = "sender", cascade = REMOVE)
	private List<ChatRoom> senderChatRoomList = new ArrayList<>();

	@OneToMany(mappedBy = "receiver", cascade = CascadeType.REMOVE)
	private List<Sse> receiverSseList = new ArrayList<>();

	@OneToMany(mappedBy = "sender", cascade = CascadeType.REMOVE)
	private List<Sse> senderSseList = new ArrayList<>();

	@PrePersist
	public void prePersist() {
		if (this.location == null) {
			this.location = "...";
		}

		if (this.profileImage == null) {
			this.profileImage = "https://s3.ap-northeast-2.amazonaws.com/main19-bucket/member/profileImage/5ce172e0-35c9-4453-bba2-6b97af732a36.png";
		}

		if (this.profileText == null) {
			this.profileText = "...";
		}
	}
}
