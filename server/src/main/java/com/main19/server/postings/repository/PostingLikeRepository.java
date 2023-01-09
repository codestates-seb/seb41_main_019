package com.main19.server.postings.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main19.server.postings.entity.PostingLike;

public interface PostingLikeRepository extends JpaRepository<PostingLike, Long> {
	Long countPostingLikesByPosting_PostingId(Long postingId);
}
