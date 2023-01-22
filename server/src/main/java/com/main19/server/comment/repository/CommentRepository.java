package com.main19.server.comment.repository;


import com.main19.server.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Page<Comment> findByPosting_PostingId(long postingId, Pageable pageable);
}
