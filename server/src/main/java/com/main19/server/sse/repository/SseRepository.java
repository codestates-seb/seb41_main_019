package com.main19.server.sse.repository;

import com.main19.server.sse.entity.Sse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SseRepository extends JpaRepository<Sse,Long> {

    @Query(value = "SELECT * FROM SSE WHERE MEMBER_ID = :num", nativeQuery = true)
    Page<Sse> findSse(@Param("num") long memberId, Pageable pageable);
}
