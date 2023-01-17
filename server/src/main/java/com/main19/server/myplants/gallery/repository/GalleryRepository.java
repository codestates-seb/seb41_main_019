package com.main19.server.myplants.gallery.repository;

import com.main19.server.myplants.gallery.entity.Gallery;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {

    List<Gallery> findByMyPlants_MyPlantsId(long myPlantsId);
}
