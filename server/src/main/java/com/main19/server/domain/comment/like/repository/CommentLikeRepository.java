package com.main19.server.domain.comment.like.repository;

import com.main19.server.domain.comment.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {

    CommentLike findByMember_MemberIdAndComment_CommentId(long memberId,long commentId);
}
