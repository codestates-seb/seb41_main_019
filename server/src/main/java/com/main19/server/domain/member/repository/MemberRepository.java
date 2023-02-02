package com.main19.server.domain.member.repository;

import com.main19.server.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Member findByUserName(String search);

    @Query(value = "SELECT m FROM Member m WHERE m.email = :email")
    Member checkEmail(String email);

    Page<Member> findByUserNameContaining(String search, Pageable pageable);
}
