package com.main19.server.domain.posting.tags.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main19.server.domain.posting.tags.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	Optional<Tag> findByTagName(String tagName);
}
