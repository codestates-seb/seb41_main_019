package com.main19.server.comment.like.repository;

import com.main19.server.comment.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {

    CommentLike findByMember_MemberIdAndComment_CommentId(long memberId,long commentId);
}
