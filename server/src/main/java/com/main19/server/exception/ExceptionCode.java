package com.main19.server.exception;

import lombok.Getter;

public enum ExceptionCode {
	FORBIDDEN(403, "Forbidden"),
	MEMBER_NOT_FOUND(404, "Member not found"),
	MEMBER_EXISTS(409, "Member exists"),
	POSTING_NOT_FOUND(404, "Posting not found"),
	POSTING_LIKE_NOT_FOUND(404, "Posting like not found"),
	TAG_NOT_FOUND(404, "Tag not found"),
	WRONG_POSTING_MEDIA(404, "Wrong posting media"), // 첨부파일 없이 포스팅 할 시
	WRONG_MEDIA_FORMAT(404, "Wrong media format"), // 지원하는 포맷이 아닐 때
	MEDIA_UPLOAD_ERROR(404, "Media upload error"), // s3에 업로드하다가 뭐 안됐을 때
	MEDIA_NOT_FOUND(404, "Media not found"), // 찾는 첨부파일 없을 때 (s3, media테이블 공통)
	POSTING_REQUIRES_AT_LEAST_ONE_MEDIA(404, "Posting requires at least one media"),
	// 첨부파일 없이 포스팅 할 시, 포스팅 수정 시 사진 다 지우지 못하도록
	POSTING_REQUIRES_LESS_THAN_TEN_MEDIA(404, "Posting requires less than ten media"),
	// 첨부파일 너무 많으면 안대
	COMMENT_NOT_FOUND(404,"Comment Not Found"),
	COMMENT_LIKE_NOT_FOUND(404,"Comment Not Found"),
	COMMENT_LIKE_ERROR(405,"You already pressed like"),
	CHATROOM_NOT_FOUND(404, "ChatRoom Not Found"),
	CHATROOM_EXISTS(409, "ChatRoom Already Exists");


	@Getter
	private int status;

	@Getter
	private String message;

	ExceptionCode(int code, String message) {
		this.status = code;
		this.message = message;
	}
}
