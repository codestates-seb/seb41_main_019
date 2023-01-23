package com.main19.server.posting.repository;

import com.main19.server.member.entity.Member;
import com.main19.server.posting.tags.entity.Tag;
import org.hibernate.sql.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.main19.server.posting.entity.Posting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Long> {
    Page<Posting> findByMember_MemberId(long memberId, Pageable pageable);

    @Query(value = "SELECT * FROM POSTING AS POST LEFT JOIN MEMBER AS MEMBER LEFT JOIN FOLLOW AS FOLLOW WHERE POST.MEMBER_ID = FOLLOW.FOLLOWER_MEMBER_ID AND MEMBER.MEMBER_ID = FOLLOW.FOLLOWING_MEMBER_ID AND FOLLOW.FOLLOWING_MEMBER_ID = :num", nativeQuery = true)
    Page<Posting> findByMember_FollowingList(@Param("num") long memberId, Pageable pageable);
}
