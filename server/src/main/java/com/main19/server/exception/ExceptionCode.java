package com.main19.server.exception;

import lombok.Getter;

public enum ExceptionCode {
	FORBIDDEN(403, "Forbidden"),
	MEMBER_NOT_FOUND(404, "Member not found"),
	MEMBER_EXISTS(409, "Member exists"),
	SAME_MEMBER(400, "can not follow same Member"),
	MEMBER_UNAUTHORIZED(401, "Member has not authorize"),
	POSTING_NOT_FOUND(404, "Posting not found"),
	POSTING_LIKE_NOT_FOUND(404, "Posting like not found"),
	POSTING_LIKE_ERROR(409,"You already pressed like"),
	TAG_NOT_FOUND(404, "Tag not found"),
	SCRAP_NOT_FOUND(404, "Scrap not found"),
	SCRAP_ALREADY_EXIST(409, "Scrap already exist"),
	WRONG_POSTING_MEDIA(405, "Wrong posting media"), // 첨부파일 없이 포스팅 할 시
	WRONG_MEDIA_FORMAT(405, "Wrong media format"), // 지원하는 포맷이 아닐 때
	MEDIA_UPLOAD_ERROR(404, "Media upload error"), // s3에 업로드하다가 뭐 안됐을 때
	MEDIA_NOT_FOUND(404, "Media not found"), // 찾는 첨부파일 없을 때 (s3, media테이블 공통)
	POSTING_MEDIA_ERROR(405, "Posting requires at least one media or less than ten media"),
	COMMENT_NOT_FOUND(404,"Comment Not Found"),
	COMMENT_LIKE_NOT_FOUND(404,"Comment Not Found"),
	COMMENT_LIKE_EXISTS(409,"You Already Pressed Like"),
	CHATROOM_NOT_FOUND(404, "ChatRoom Not Found"),
	CHATROOM_EXISTS(409, "ChatRoom Already Exists"),
	NOTIFICATION_NOT_FOUND(404, "Notification Not Found");


	@Getter
	private int status;

	@Getter
	private String message;

	ExceptionCode(int code, String message) {
		this.status = code;
		this.message = message;
	}
}
