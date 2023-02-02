package com.main19.server.domain.member.dto;

import com.main19.server.domain.follow.dto.FollowDto;
import com.main19.server.domain.posting.scrap.dto.ScrapResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


public class MemberDto {
	@Getter
	@AllArgsConstructor
	public static class Post {
		@NotBlank
		@Size(min = 2, max = 6, message = "아이디는 2자 이상 6자 이하여야 합니다.")
		@Pattern(regexp = "^[0-9a-zA-Z가-힣]*$", message = "아이디는 영어, 숫자만 가능합니다.")
		private String userName;

		@Size(min = 4, max = 12, message = "아이디는 4자 이상 12자 이하여야 합니다.")
		@Pattern(regexp = "^[0-9a-zA-Z]*$", message = "영문 또는 숫자")
		private String email;

		private String location;

		private String profileText;

		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
		message = "최소 8 자, 하나 이상의 문자, 하나의 숫자, 하나의 특수 문자")
		private String password;
	}

	@Getter
	public static class Patch {
		private long memberId;

		@Size(min = 2, max = 12, message = "아이디는 2자 이상 12자 이하여야 합니다.")
		@Pattern(regexp = "^[0-9a-zA-Z가-힣]*$", message = "아이디는 영어, 숫자만 가능합니다.")
		private String userName;

		private String profileText;
		private String location;

		public void setMemberId(long memberId) {
			this.memberId = memberId;
		}

		@Builder
		public Patch(String userName, String profileText, String location) {
			this.userName = userName;
			this.profileText = profileText;
			this.location = location;
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class Response {
		private long memberId;
		private String userName;
		private String email;
		private String location;
		private String profileImage;
		private String profileText;
		private List<ScrapResponseDto> scrapPostingList;
		private List<FollowDto.FollowingResponse> followingList;
		private List<FollowDto.FollowerResponse> followerList;
	}

}
