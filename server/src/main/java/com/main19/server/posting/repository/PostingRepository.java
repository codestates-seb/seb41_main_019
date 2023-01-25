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

    @Query(value = "SELECT * FROM POSTING AS P LEFT JOIN FOLLOW AS F ON P.MEMBER_ID = F.FOLLOWER_MEMBER_ID LEFT JOIN MEMBER AS M ON M.MEMBER_ID = F.FOLLOWING_MEMBER_ID WHERE F.FOLLOWING_MEMBER_ID = :num", nativeQuery = true)
    Page<Posting> findByMember_FollowingList(@Param("num") long memberId, Pageable pageable);

    @Query(value = "SELECT * FROM POSTING AS P LEFT JOIN POSTING_TAGS AS PT on PT.POSTING_ID = P.POSTING_ID LEFT JOIN TAG AS T on PT.TAG_ID = T.TAG_ID WHERE T.TAG_ID = :num", nativeQuery = true)
    Page<Posting> findPostingsByTags(@Param("num")long tagId, Pageable pageable);
}
