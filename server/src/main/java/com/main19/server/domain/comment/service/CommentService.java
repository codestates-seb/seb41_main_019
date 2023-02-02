package com.main19.server.domain.comment.service;

import com.main19.server.global.auth.jwt.JwtTokenizer;
import com.main19.server.domain.comment.entity.Comment;
import com.main19.server.domain.comment.repository.CommentRepository;
import com.main19.server.global.exception.BusinessLogicException;
import com.main19.server.global.exception.ExceptionCode;
import com.main19.server.domain.member.entity.Member;
import com.main19.server.domain.member.service.MemberService;
import com.main19.server.domain.posting.entity.Posting;
import com.main19.server.domain.posting.service.PostingService;
import com.main19.server.domain.sse.entity.Sse.SseType;
import com.main19.server.domain.sse.service.SseService;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostingService postingService;
    private final MemberService memberService;
    private final SseService sseService;
    private final JwtTokenizer jwtTokenizer;

    public Comment createComment(Comment comment, long postingId, long memberId, String token) {

        if (memberId != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        Posting posting = postingService.findPosting(postingId);
        Member member =  memberService.findMember(memberId);

        comment.setPosting(posting);
        comment.setMember(member);

        posting.createCommentCount();

        if(posting.getMemberId() != jwtTokenizer.getMemberId(token)) {
            sseService.sendPosting(posting.getMember(), SseType.comment, member, comment.getPosting());
        }

        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comments, String token) {

        if (comments.getMemberId() != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        Comment comment = findVerifiedComment(comments.getCommentId());

        Optional.ofNullable(comments.getComment())
            .ifPresent(comment::setComment);

        comment.setModifiedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public Comment findComment(long commentId) {

        return findVerifiedComment(commentId);
    }

    @Transactional(readOnly = true)
    public Page<Comment> findComments(long postingId, int page, int size) {
        return commentRepository.findByPosting_PostingId(postingId,PageRequest.of(page, size,
            Sort.by("commentId").descending()));
    }

    public void deleteComment(long commentId, String token) {

        if (findComment(commentId).getMemberId() != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        Comment comment = findVerifiedComment(commentId);

        comment.getPosting().deleteCommentCount();

        commentRepository.delete(comment);
    }

    private Comment findVerifiedComment(long commentId){
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment findComment = optionalComment.orElseThrow(()->new BusinessLogicException(
            ExceptionCode.COMMENT_NOT_FOUND));
        return findComment;
    }
}
