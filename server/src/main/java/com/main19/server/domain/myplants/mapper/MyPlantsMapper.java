package com.main19.server.domain.myplants.mapper;

import com.main19.server.domain.myplants.gallery.dto.GalleryDto;
import com.main19.server.domain.myplants.gallery.entity.Gallery;
import com.main19.server.domain.myplants.dto.MyPlantsDto;
import com.main19.server.domain.myplants.entity.MyPlants;

import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MyPlantsMapper {

    MyPlants myPlantsPostDtoToMyPlants(MyPlantsDto.Post requestBody);

    MyPlantsDto.Response myPlantsToMyPlantsResponseDto(MyPlants myPlants);

    MyPlants myPlantsPatchDtoToMyPlants(MyPlantsDto.PlantsPatch requestBody);

    List<MyPlantsDto.Response> myPlantsListToMyPlantsResponseDto(List<MyPlants> myPlants);

    GalleryDto.MyPlantsResponse galleryToGalleryMyPlantsResponseDto(Gallery gallery);
}
