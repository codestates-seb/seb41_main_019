package com.main19.server.member.dto;

import lombok.AllArgsConstructor;
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
		private long memberId;
		private String userName;
		private String profileImage;
		private String profileText;
		private String location;

		public void setMemberId(long memberId) {
			this.memberId = memberId;
		}
	}

	@Getter
	@Builder
	public static class Delete {
		private long memberId;
	}

	@Getter
	public static class Response {
		private long memberId;
		private String userName;
		private String email;
		private String location;
		private String profileImage;
		private String profileText;

		@Builder
		public Response(long memberId, String userName, String email, String location, String profileImage, String profileText) {
			this.memberId = memberId;
			this.userName = userName;
			this.email = email;
			this.location = location;
			this.profileImage = profileImage;
			this.profileText = profileText;
		}
	}
}
