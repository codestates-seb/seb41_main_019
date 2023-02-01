package com.main19.server.domain.posting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.main19.server.domain.posting.entity.Posting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostingRepository extends JpaRepository<Posting, Long> {
    Page<Posting> findByMember_MemberId(long memberId, Pageable pageable);

    @Query(value = "select * from posting where member_id in (select f.follower_member_id from follow f where f.following_member_id = :num)", nativeQuery = true)
    Page<Posting> findByMember_FollowingList(@Param("num") long memberId, Pageable pageable);

    @Query(value = "select * from posting p where posting_content like :str", nativeQuery = true)
    Page<Posting> findPostingsByPostingContent(@Param("str") String str, Pageable pageable);

    @Query(value = "select * from posting where posting_id in (select pt.posting_id from posting_tags pt where pt.tag_id in (select t.tag_id from tag t where t.tag_name like :str))", nativeQuery = true)
    Page<Posting> findPostingsByTagName(@Param("str") String str, Pageable pageable);

//    @Query(value = "SELECT * FROM POSTING AS P LEFT JOIN POSTING_TAGS AS PT on PT.POSTING_ID = P.POSTING_ID LEFT JOIN TAG AS T on PT.TAG_ID = T.TAG_ID WHERE T.TAG_ID = :num", nativeQuery = true)
//    Page<Posting> findPostingsByTags(@Param("num")long tagId, Pageable pageable);
}
