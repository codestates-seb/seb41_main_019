package com.main19.server.posting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main19.server.posting.entity.Media;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED)
public interface MediaRepository extends JpaRepository<Media, Long> {
}
