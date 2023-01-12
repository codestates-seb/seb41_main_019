package com.main19.server.posting.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main19.server.posting.like.entity.PostingLike;

public interface PostingLikeRepository extends JpaRepository<PostingLike, Long> {
    PostingLike findByMember_MemberIdAndPosting_PostingId(long memberId, long postingId);
}
