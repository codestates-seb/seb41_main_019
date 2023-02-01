package com.main19.server.domain.posting.scrap.repository;

import com.main19.server.domain.posting.scrap.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Scrap findByMember_MemberIdAndPosting_PostingId(long memberId, long postingId);
}
