package com.main19.server.domain.sse.repository;

import com.main19.server.domain.sse.entity.Sse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SseRepository extends JpaRepository<Sse,Long> {

    @Query(value = "SELECT * FROM SSE WHERE MEMBER_ID = :num AND IS_READ = FALSE", nativeQuery = true)
    Page<Sse> findSse(@Param("num") long memberId, Pageable pageable);
    @Query(value = "SELECT * FROM SSE WHERE MEMBER_ID = :num AND IS_READ = FALSE", nativeQuery = true)
    List<Sse> findAllSse(@Param("num") long memberId);
}
