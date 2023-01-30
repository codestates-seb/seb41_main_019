package com.main19.server.member.dto;

import com.main19.server.follow.dto.FollowDto;
import com.main19.server.posting.scrap.dto.ScrapResponseDto;
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
		@Size(min = 3, max = 12, message = "아이디는 3자 이상 12자 이하여야 합니다.")
		@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영어, 숫자만 가능합니다.")
		private String userName;
    
		@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영문 또는 숫자")
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
}
