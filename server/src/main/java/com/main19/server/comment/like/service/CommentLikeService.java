package com.main19.server.comment.like.service;

import com.main19.server.comment.entity.Comment;
import com.main19.server.comment.like.entity.CommentLike;
import com.main19.server.comment.like.repository.CommentLikeRepository;
import com.main19.server.comment.service.CommentService;
import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.member.service.MemberService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final MemberService memberService;
    private final CommentService commentService;

    public CommentLike createLike(CommentLike commentLike, long commentId, long memberId) {

        CommentLike findCommentLike = commentLikeRepository.findByMember_MemberIdAndComment_CommentId(memberId,commentId);

        if(findCommentLike != null) {
            throw new BusinessLogicException(ExceptionCode.COMMENT_LIKE_ERROR);
        }

        commentLike.setMember(memberService.findMember(memberId));

        Comment comment = commentService.findComment(commentId);

        comment.setLikeCount(comment.getLikeCount()+1);

        commentLike.setComment(comment);


        return commentLikeRepository.save(commentLike);
    }

    public void deleteLike(long commentLikeId) {

        CommentLike commentLike = findVerifiedCommentLike(commentLikeId);

        commentLike.getComment().setLikeCount(commentLike.getComment().getLikeCount()-1);

        commentLikeRepository.delete(commentLike);

    }

    private CommentLike findVerifiedCommentLike(long commentLikeId) {
        Optional<CommentLike> optionalCommentLike = commentLikeRepository.findById(commentLikeId);
        CommentLike findCommentLike =
            optionalCommentLike.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_LIKE_NOT_FOUND));
        return findCommentLike;
    }
}
