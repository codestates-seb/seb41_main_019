package com.main19.server.postings.tags.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main19.server.postings.tags.entity.PostingTags;

public interface PostingTagsRepository extends JpaRepository<PostingTags, Long> {
	List<PostingTags> findAllByPosting_PostingId(long postingId);
}
