package com.main19.server.domain.myplants.repository;

import com.main19.server.domain.myplants.entity.MyPlants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyPlantsRepository extends JpaRepository<MyPlants, Long> {
    List<MyPlants> findByMember_MemberId(long memberId);
    Page<MyPlants> findByMember_MemberId(long memberId, Pageable pageable);
}
