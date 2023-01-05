package com.main19.server.exception;

import lombok.Getter;

public enum ExceptionCode {
	FORBIDDEN(403, "Forbidden"),
	MEMBER_NOT_FOUND(404, "Member not found"),
	MEMBER_EXISTS(409, "Member exists");


	@Getter
	private int status;

	@Getter
	private String message;

	ExceptionCode(int code, String message) {
		this.status = code;
		this.message = message;
	}
}
