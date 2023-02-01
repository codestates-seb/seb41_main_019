package com.main19.server.domain.follow.repository;

import com.main19.server.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("select f from Follow f where f.followingId.memberId = :followingId and f.followerId.memberId = :followerId ")
    Optional<Follow> findFollowId(long followingId, long followerId);

    @Query("select f from Follow f where f.followingId.memberId = :followingId and f.followerId.memberId = :followerId ")
    Follow findFollow(long followingId, long followerId);
}
