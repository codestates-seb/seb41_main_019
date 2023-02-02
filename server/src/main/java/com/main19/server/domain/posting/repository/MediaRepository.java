package com.main19.server.domain.posting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main19.server.domain.posting.entity.Media;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED)
public interface MediaRepository extends JpaRepository<Media, Long> {
}
