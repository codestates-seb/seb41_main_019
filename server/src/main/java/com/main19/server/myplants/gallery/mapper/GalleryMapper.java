package com.main19.server.myplants.gallery.mapper;

import com.main19.server.myplants.gallery.dto.GalleryDto;
import com.main19.server.myplants.gallery.entity.Gallery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GalleryMapper {

    Gallery galleryDtoPostToGallery(GalleryDto.Post gallery);

    @Mapping(source = "myPlants.myPlantsId" , target = "myPlantsId")
    GalleryDto.Response galleryToGalleryDtoResponse(Gallery gallery);
}
