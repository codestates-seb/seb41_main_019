package com.main19.server.myplants.repository;

import com.main19.server.myplants.entity.MyPlants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPlantsRepository extends JpaRepository<MyPlants, Long> {

}
