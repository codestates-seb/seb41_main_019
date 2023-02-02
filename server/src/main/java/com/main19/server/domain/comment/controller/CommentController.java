package com.main19.server.domain.comment.controller;

import com.main19.server.domain.comment.dto.CommentDto;
import com.main19.server.domain.comment.entity.Comment;
import com.main19.server.domain.comment.mapper.CommentMapper;
import com.main19.server.domain.comment.service.CommentService;
import com.main19.server.global.dto.MultiResponseDto;
import com.main19.server.global.dto.SingleResponseDto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(("/comments"))
@Validated
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping("/{posting-id}")
    public ResponseEntity postComment(@RequestHeader(name = "Authorization") String token,
        @PathVariable("posting-id") @Positive long postingId,
        @Valid @RequestBody CommentDto.Post commentPostDto) {

        Comment comment = commentMapper.commentsPostDtoToComments(commentPostDto);
        Comment createdComment = commentService.createComment(comment, postingId,
            commentPostDto.getMemberId(),token);

        CommentDto.Response response = commentMapper.commentsToCommentsResponseDto(createdComment);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @PatchMapping("/{comment-id}")
    public ResponseEntity patchComment(@RequestHeader(name = "Authorization") String token,
        @PathVariable("comment-id") @Positive long commentId,
        @Valid @RequestBody CommentDto.Patch commentPatchDto) {

        commentPatchDto.setCommentId(commentId);

        Comment response = commentService.updateComment(
            commentMapper.commentsPatchDtoToComments(commentPatchDto),token);

        return new ResponseEntity<>(
            new SingleResponseDto<>(commentMapper.commentsToCommentsResponseDto(response)),
            HttpStatus.OK);
    }

    @GetMapping("/{posting-id}")
    public ResponseEntity getComments(@PathVariable("posting-id") @Positive long postingId,
        @Positive @RequestParam int page, @Positive @RequestParam int size) {

        Page<Comment> pageComments = commentService.findComments(postingId,page - 1, size);
        List<Comment> response = pageComments.getContent();

        return new ResponseEntity<>(
            new MultiResponseDto<>(commentMapper.commentsToCommentsResponseDtos(response),
                pageComments), HttpStatus.OK);
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity deleteComment(@RequestHeader(name = "Authorization") String token,
        @PathVariable("comment-id") @Positive long commentId) {

        commentService.deleteComment(commentId,token);

        return ResponseEntity.noContent().build();
    }
}
