package com.main19.server.postings.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main19.server.dto.SingleResponseDto;
import com.main19.server.postings.dto.PostingLikeDto;
import com.main19.server.postings.entity.PostingLike;
import com.main19.server.postings.mapper.PostingMapper;
import com.main19.server.postings.service.PostingLikeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/posting")
@Validated
@Slf4j
@RequiredArgsConstructor
public class PostingLikeController {
	private final PostingLikeService postingLikeService;
	private final PostingMapper mapper;

	@PostMapping("/{posting-id}/likes")
	public ResponseEntity postPosingLike(@PathVariable("posting-id") @Positive long postingId, @Valid @RequestBody PostingLikeDto requestBody) {
		PostingLike postingLike = postingLikeService.createPostingLike(mapper.postingLikeDtoToPostingLike(requestBody),
			postingId, requestBody.getMemberId());
		return new ResponseEntity<>(new SingleResponseDto<>(mapper.postingLikeToPostingLikeResponseDto(postingLike)), HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/likes/{posting-like-id}")
	public ResponseEntity deletePostingLike(@PathVariable("posting-like-id") @Positive long postingLikeId) {
		postingLikeService.deletePostingLike(postingLikeId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
