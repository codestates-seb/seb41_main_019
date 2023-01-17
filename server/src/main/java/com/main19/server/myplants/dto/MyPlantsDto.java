package com.main19.server.myplants.dto;

import com.main19.server.myplants.gallery.dto.GalleryDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class MyPlantsDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        private long memberId;
        private String plantName;

    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        private long galleryId;
        private int changeNumber;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private long myPlantsId;
        private String plantName;
        private List<GalleryDto.MyPlantsResponse> galleryList;
    }




}
