package com.main19.server.posting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main19.server.posting.entity.Media;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
