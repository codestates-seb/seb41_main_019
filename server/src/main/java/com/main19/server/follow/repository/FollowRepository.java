package com.main19.server.follow.repository;

import com.main19.server.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("select f from Follow f where f.followingId.memberId = :followingId and f.followedId.memberId = :followedId ")
    Optional<Follow> findFollowId(long followingId, long followedId);
}
