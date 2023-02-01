package com.main19.server.global.advice;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.main19.server.global.exception.BusinessLogicException;
import com.main19.server.global.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		final ErrorResponse response = ErrorResponse.of(e.getBindingResult());

		return response;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
		final ErrorResponse response = ErrorResponse.of(e.getConstraintViolations());

		return response;
	}

	@ExceptionHandler
	public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {
		final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

		return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode()
			.getStatus()));
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ErrorResponse handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException e) {

		final ErrorResponse response = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED);

		return response;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleHttpMessageNotReadableException(
		HttpMessageNotReadableException e) {

		final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST,
			"Required request body is missing");

		return response;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMissingServletRequestParameterException(
		MissingServletRequestParameterException e) {

		final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST,
			e.getMessage());

		return response;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorResponse MissingRequestHeaderException(
		MissingRequestHeaderException e) {

		final ErrorResponse response = ErrorResponse.of(HttpStatus.UNAUTHORIZED,e.getMessage());

		return response;
	}


	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		log.error(" handle Exception: Duplicated Unique Column", e);

		final ErrorResponse response = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "Duplicated Unique Column");

		return response;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {

		final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());

		return response;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleException(Exception e) {
		log.error("# handle Exception", e);

		final ErrorResponse response = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);

		return response;
	}
}
