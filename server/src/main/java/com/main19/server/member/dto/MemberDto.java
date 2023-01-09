package com.main19.server.member.dto;

import lombok.Builder;
import lombok.Getter;


public class MemberDto {
	@Getter
	public static class Post {
		private String username;
		private String email;
		private int postingCount;

		@Builder
		public Post(String username, String email, int postingCount) {
			this.username = username;
			this.email = email;
			this.postingCount = postingCount;
		}
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
		private String name;
		private String email;
		private int postingCount;

		@Builder
		public Response(Long memberId, String name, String email, int postingCount) {
			this.memberId = memberId;
			this.name = name;
			this.email = email;
			this.postingCount = postingCount;
		}
	}
}
