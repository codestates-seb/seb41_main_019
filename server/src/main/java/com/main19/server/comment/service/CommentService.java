package com.main19.server.comment.service;

import com.main19.server.comment.entity.Comment;
import com.main19.server.comment.repository.CommentRepository;
import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.member.entity.Member;
import com.main19.server.member.service.MemberService;
import com.main19.server.postings.entity.Posting;
import com.main19.server.postings.service.PostingService;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostingService postingService;
    private final MemberService memberService;

    public Comment createComment(Comment comment, long postingId, long memberId) {

        Posting posting = postingService.findPosting(postingId);
        Member member =  memberService.findMember(memberId);

        comment.setPosting(posting);
        comment.setMember(member);

        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comments) {

        Comment comment = findVerifiedComment(comments.getCommentId());

        Optional.ofNullable(comments.getComment())
            .ifPresent(comment::setComment);

        comment.setModifiedAt(LocalDateTime.now());

        comments.setModifiedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    public Comment findComment(long commentId) {

        return findVerifiedComment(commentId);
    }

    public Page<Comment> findComments(int page, int size) {
        return commentRepository.findAll(PageRequest.of(page, size,
            Sort.by("commentId").descending()));
    }

    public void deleteComment(long commentId) {
        Comment comment = findVerifiedComment(commentId);

        commentRepository.delete(comment);
    }

    private Comment findVerifiedComment(long commentId){
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment findComment = optionalComment.orElseThrow(()->new BusinessLogicException(
            ExceptionCode.COMMENT_NOT_FOUND));
        return findComment;
    }
}