package com.main19.server.sse.repository;

import com.main19.server.sse.entity.Sse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SseRepository extends JpaRepository<Sse,Long> {

}
