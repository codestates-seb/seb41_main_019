package com.main19.server.domain.posting.like.repository;

import com.main19.server.domain.posting.like.entity.PostingLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingLikeRepository extends JpaRepository<PostingLike, Long> {
    PostingLike findByMember_MemberIdAndPosting_PostingId(long memberId, long postingId);
}
