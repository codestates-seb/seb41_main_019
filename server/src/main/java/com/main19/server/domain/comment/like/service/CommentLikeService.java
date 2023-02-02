package com.main19.server.domain.comment.like.service;

import com.main19.server.global.auth.jwt.JwtTokenizer;
import com.main19.server.domain.comment.entity.Comment;
import com.main19.server.domain.comment.like.entity.CommentLike;
import com.main19.server.domain.comment.like.repository.CommentLikeRepository;
import com.main19.server.domain.comment.service.CommentService;
import com.main19.server.global.exception.BusinessLogicException;
import com.main19.server.global.exception.ExceptionCode;
import com.main19.server.domain.member.service.MemberService;

import com.main19.server.domain.sse.entity.Sse.SseType;
import com.main19.server.domain.sse.service.SseService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final MemberService memberService;
    private final CommentService commentService;
    private final SseService sseService;
    private final JwtTokenizer jwtTokenizer;

    public CommentLike createLike(long commentId, long memberId, String token) {

        long tokenId = jwtTokenizer.getMemberId(token);

        if (memberId != tokenId) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        CommentLike findCommentLike = commentLikeRepository.findByMember_MemberIdAndComment_CommentId(memberId,commentId);

        if(findCommentLike != null) {
            throw new BusinessLogicException(ExceptionCode.COMMENT_LIKE_EXISTS);
        }
        CommentLike commentLike = new CommentLike();

        commentLike.setMember(memberService.findMember(memberId));

        Comment comment = commentService.findComment(commentId);

        comment.setLikeCount(comment.getLikeCount()+1);

        commentLike.setComment(comment);

        if(comment.getMemberId() != tokenId) {
            sseService.sendPosting(comment.getMember(), SseType.commentLike, memberService.findMember(memberId),comment.getPosting());
        }

        return commentLikeRepository.save(commentLike);
    }

    public void deleteLike(long commentLikeId, String token) {

        if (findVerifiedCommentLike(commentLikeId).getMemberId() != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        CommentLike commentLike = findVerifiedCommentLike(commentLikeId);

        commentLike.getComment().setLikeCount(commentLike.getCommentLikeCount()-1);

        commentLikeRepository.delete(commentLike);

    }

    @Transactional(readOnly = true)
    private CommentLike findVerifiedCommentLike(long commentLikeId) {
        Optional<CommentLike> optionalCommentLike = commentLikeRepository.findById(commentLikeId);
        CommentLike findCommentLike =
            optionalCommentLike.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_LIKE_NOT_FOUND));
        return findCommentLike;
    }
}
