package com.main19.server.domain.myplants.gallery.mapper;

import com.main19.server.domain.myplants.gallery.dto.GalleryDto;
import com.main19.server.domain.myplants.gallery.entity.Gallery;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GalleryMapper {

    Gallery galleryDtoPostToGallery(GalleryDto.Post gallery);

    List<GalleryDto.Response> galleryListToGalleryResponseList(List<Gallery> galleryList);

    @Mapping(source = "myPlants.myPlantsId" , target = "myPlantsId")
    GalleryDto.Response galleryToGalleryDtoResponse(Gallery gallery);
}
