package com.main19.server.global.exception;

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
	FOLLOW_NOT_FOUND(404, "Follow not found"),
	FOLLOW_ALREADY_EXIST(409, "Follow already exist"),
	WRONG_POSTING_MEDIA(405, "Wrong posting media"),
	WRONG_MEDIA_FORMAT(405, "Wrong media format"),
	MEDIA_UPLOAD_ERROR(404, "Media upload error"),
	MEDIA_NOT_FOUND(404, "Media not found"),
	POSTING_MEDIA_ERROR(405, "Posting requires at least 1 media or less than 3 medias"),
	COMMENT_NOT_FOUND(404,"Comment Not Found"),
	COMMENT_LIKE_NOT_FOUND(404,"Comment Not Found"),
	COMMENT_LIKE_EXISTS(409,"You Already Pressed Like"),
	CHATROOM_NOT_FOUND(404, "ChatRoom Not Found"),
	CHATROOM_EXISTS(409, "ChatRoom Already Exists"),
	NOTIFICATION_NOT_FOUND(404, "Notification Not Found"),
	MYPLANTS_NOT_FOUND(404, "My Plants Not Found"),
	GALLERY_NOT_FOUND(404, "Gallery Not Found"),
	CONVERSION_FAILED(404, "File conversion failed"),
	CHATROOM_NOT_DELETE(409, "ChatRoom Not Delete");


	@Getter
	private int status;

	@Getter
	private String message;

	ExceptionCode(int code, String message) {
		this.status = code;
		this.message = message;
	}
}
