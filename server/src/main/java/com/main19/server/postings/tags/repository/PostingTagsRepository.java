package com.main19.server.postings.tags.repository;

import com.main19.server.postings.tags.entity.PostingTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostingTagsRepository extends JpaRepository<PostingTags, Long> {
}
