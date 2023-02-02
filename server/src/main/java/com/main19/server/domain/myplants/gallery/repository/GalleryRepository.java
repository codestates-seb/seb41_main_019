package com.main19.server.domain.myplants.gallery.repository;

import com.main19.server.domain.myplants.gallery.entity.Gallery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {

    List<Gallery> findByMyPlants_MyPlantsId(long myPlantsId);
    @Query(value = "SELECT * FROM GALLERY WHERE MY_PLANTS_ID = :num", nativeQuery = true)
    Page<Gallery> findByMyPlants_MyPlantsId(@Param("num") long myPlantsId, Pageable pageable);
}
