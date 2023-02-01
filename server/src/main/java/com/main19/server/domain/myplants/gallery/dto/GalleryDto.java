package com.main19.server.domain.myplants.gallery.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GalleryDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {

        @NotBlank
        private String content;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private long myPlantsId;
        private long galleryId;
        private String content;
        private String plantImage;
        private LocalDateTime createdAt;

    }

    @Getter
    @AllArgsConstructor
    public static class MyPlantsResponse {

        private long galleryId;

    }

}
