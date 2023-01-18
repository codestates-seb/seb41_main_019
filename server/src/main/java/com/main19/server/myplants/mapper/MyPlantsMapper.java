package com.main19.server.myplants.mapper;

import com.main19.server.myplants.dto.MyPlantsDto;
import com.main19.server.myplants.entity.MyPlants;
import com.main19.server.myplants.gallery.dto.GalleryDto;
import com.main19.server.myplants.gallery.entity.Gallery;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MyPlantsMapper {

    MyPlants myPlantsPostDtoToMyPlants(MyPlantsDto.Post requestBody);

    MyPlantsDto.Response myPlantsToMyPlantsResponseDto(MyPlants myPlants);

    List<MyPlantsDto.Response> myPlantsListToMyPlantsResponseDto(List<MyPlants> myPlants);

    GalleryDto.MyPlantsResponse galleryToGalleryMyPlantsResponseDto(Gallery gallery);
}
