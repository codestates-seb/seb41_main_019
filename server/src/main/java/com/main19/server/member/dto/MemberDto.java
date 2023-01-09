package com.main19.server.member.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


public class MemberDto {
	@Getter
	public static class Post {
		@NotBlank
		private String userName;
		@Email
		private String email;
		private String location;
		private String profileImage;
		private String profileText;
	}

	@Getter
	public static class Patch {
		private Long memberId;
		private String username;
		private String profileImage;
		private String profileText;
		private String belong;

		@Builder
		public Patch(Long memberId, String username, String profileImage, String profileText, String belong) {
			this.memberId = memberId;
			this.username = username;
			this.profileImage = profileImage;
			this.profileText = profileText;
			this.belong = belong;
		}
	}

	@Getter
	@Builder
	public static class Delete {
		private Long memberId;
	}

	@Getter
	public static class Response {
		private Long memberId;
		private String userName;
		private String email;
		private String location;
		private String profileImage;
		private String profileText;

		@Builder
		public Response(Long memberId, String userName, String email, String location, String profileImage, String profileText) {
			this.memberId = memberId;
			this.userName = userName;
			this.email = email;
			this.location = location;
			this.profileImage = profileImage;
			this.profileText = profileText;
		}
	}
}
