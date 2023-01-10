package com.main19.server.postings.tags.repository;

import com.main19.server.postings.tags.entity.Tag;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
	Optional<Tag> findByTagName(String tagName);
}
