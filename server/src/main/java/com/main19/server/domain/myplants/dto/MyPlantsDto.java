package com.main19.server.domain.myplants.dto;

import com.main19.server.domain.myplants.gallery.dto.GalleryDto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class MyPlantsDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        @Positive
        private long memberId;
        @NotBlank
        private String plantName;
        @NotBlank
        private String plantType;
        @NotBlank
        private String plantBirthDay;

    }

    @Getter
    @AllArgsConstructor
    public static class PlantsPatch {

        @Setter
        @Positive
        private long myPlantsId;
        @NotBlank
        private String plantName;
        @NotBlank
        private String plantType;
        @NotBlank
        private String plantBirthDay;

    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        @Positive
        private long galleryId;
        @Positive
        private int changeNumber;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private long myPlantsId;
        private String plantName;
        private String plantType;
        private String plantBirthDay;
        private List<GalleryDto.MyPlantsResponse> galleryList;

    }




}
