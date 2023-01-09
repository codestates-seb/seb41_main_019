package com.main19.server.postings.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main19.server.postings.entity.Media;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
