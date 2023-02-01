package com.main19.server.domain.comment.like.controller;

import com.main19.server.domain.comment.like.dto.CommentLikeDto;
import com.main19.server.domain.comment.like.entity.CommentLike;
import com.main19.server.domain.comment.like.mapper.CommentLikeMapper;
import com.main19.server.domain.comment.like.service.CommentLikeService;
import com.main19.server.global.dto.SingleResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;
    private final CommentLikeMapper commentLikeMapper;

    @PostMapping("/{comment-id}/likes")
    public ResponseEntity postLike(@RequestHeader(name = "Authorization") String token,
        @PathVariable("comment-id") @Positive long commentId,
        @Valid @RequestBody CommentLikeDto.Post commentLikePostDto) {

        CommentLike createdComment = commentLikeService.createLike(commentId,
            commentLikePostDto.getMemberId(), token);
        CommentLikeDto.Response response = commentLikeMapper.commentLikeToCommentLikeResponse(
            createdComment);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @DeleteMapping("/likes/{comment-like-id}")
    public ResponseEntity deleteLike(@RequestHeader(name = "Authorization") String token,
        @PathVariable("comment-like-id") @Positive long commentLikeId) {

        commentLikeService.deleteLike(commentLikeId, token);

        return ResponseEntity.noContent().build();
    }
}
