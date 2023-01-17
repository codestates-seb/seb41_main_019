package com.main19.server.myplants.gallery.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GalleryDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {

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
