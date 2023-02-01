package com.main19.server.domain.posting.tags.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main19.server.domain.posting.tags.entity.PostingTags;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PostingTagsRepository extends JpaRepository<PostingTags, Long> {
	List<PostingTags> findAllByPosting_PostingId(long postingId);

	@Query(value = "DELETE FROM Posting_Tags WHERE POSTING_ID = :num" , nativeQuery = true)
	@Modifying
	@Transactional
	void deletePostingTagsByPostingId(@Param("num") long postingId);
}
