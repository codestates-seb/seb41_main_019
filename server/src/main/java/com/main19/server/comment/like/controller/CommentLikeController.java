package com.main19.server.comment.like.controller;

import com.main19.server.comment.like.dto.CommentLikeDto;
import com.main19.server.comment.like.entity.CommentLike;
import com.main19.server.comment.like.mapper.CommentLikeMapper;
import com.main19.server.comment.like.repository.CommentLikeRepository;
import com.main19.server.comment.like.service.CommentLikeService;
import com.main19.server.dto.MultiResponseDto;
import com.main19.server.dto.SingleResponseDto;
import com.main19.server.member.entity.Member;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;
    private final CommentLikeMapper commentLikeMapper;
    private final CommentLikeRepository commentLikeRepository;

    @PostMapping("/{comment-id}/likes")
    public ResponseEntity postLike(@PathVariable("comment-id") @Positive long commentId,
        @Valid @RequestBody CommentLikeDto.Post commentLikePostDto) {

        CommentLike commentLikes = commentLikeMapper.commentLikePostDtoToCommentLike(
            commentLikePostDto);
        CommentLike createdComment = commentLikeService.createLike(commentLikes, commentId,
            commentLikePostDto.getMemberId());
        CommentLikeDto.Response response = commentLikeMapper.commentLikeToCommentLikeResponse(
            createdComment);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @DeleteMapping("/likes/{comment-like-id}")
    public ResponseEntity deleteLike(@PathVariable("comment-like-id") @Positive long commentLikeId) {

        commentLikeService.deleteLike(commentLikeId);

        return ResponseEntity.noContent().build();
    }
}
