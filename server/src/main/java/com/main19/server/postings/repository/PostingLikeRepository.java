package com.main19.server.postings.repository;

import com.main19.server.comment.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import com.main19.server.postings.entity.PostingLike;

public interface PostingLikeRepository extends JpaRepository<PostingLike, Long> {
    PostingLike findByMember_MemberIdAndPosting_PostingId(long memberId, long postingId);
}
