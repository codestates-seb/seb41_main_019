package com.main19.server.domain.myplants.gallery.controller;

import com.main19.server.domain.myplants.service.MyPlantsService;
import com.main19.server.global.dto.MultiResponseDto;
import com.main19.server.global.dto.SingleResponseDto;
import com.main19.server.domain.myplants.entity.MyPlants;
import com.main19.server.domain.myplants.gallery.dto.GalleryDto;
import com.main19.server.domain.myplants.gallery.entity.Gallery;
import com.main19.server.domain.myplants.gallery.mapper.GalleryMapper;
import com.main19.server.domain.myplants.gallery.service.GalleryService;
import com.main19.server.global.storageService.s3.GalleryStorageService;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myplants")
public class GalleryController {

    private final GalleryStorageService storageService;
    private final GalleryMapper galleryMapper;
    private final GalleryService galleryService;
    private final MyPlantsService myPlantsService;

    @PostMapping(value = "/{myplants-id}/gallery" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postGallery(@RequestHeader(name = "Authorization") String token, @PathVariable("myplants-id") @Positive long myPlantsId,
        @Valid @RequestPart GalleryDto.Post requestBody, @RequestPart MultipartFile galleryImage) {

        String imagePath = storageService.uploadGalleryImage(galleryImage);
        Gallery gallery = galleryMapper.galleryDtoPostToGallery(requestBody);
        MyPlants myPlants = myPlantsService.findMyPlants(myPlantsId);

        Gallery createdGallery = galleryService.createGallery(gallery,myPlants,imagePath,token);

        GalleryDto.Response response = galleryMapper.galleryToGalleryDtoResponse(createdGallery);

        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @GetMapping("/gallery/{gallery-id}")
    public ResponseEntity getGallery(@PathVariable("gallery-id") @Positive long galleryId) {

        Gallery gallery = galleryService.findGallery(galleryId);
        GalleryDto.Response response = galleryMapper.galleryToGalleryDtoResponse(gallery);

        return new ResponseEntity(new SingleResponseDto<>(response),HttpStatus.OK);
    }

    @GetMapping("/{myplants-id}/gallery")
    public ResponseEntity getsGallery(@PathVariable("myplants-id") @Positive long myPlantsId,
        @RequestParam  @Positive int page, @RequestParam  @Positive int size) {

        Page<Gallery> galleryPage = galleryService.findByAllMyPlantsId(myPlantsId,page-1,size);
        List<Gallery> content = galleryPage.getContent();
        List<GalleryDto.Response> response = galleryMapper.galleryListToGalleryResponseList(content);

        return new ResponseEntity(new MultiResponseDto<>(response,galleryPage),HttpStatus.OK);
    }

    @DeleteMapping(value = "/gallery/{gallery-id}")
    public ResponseEntity deleteGallery(@RequestHeader(name = "Authorization") String token, @PathVariable("gallery-id") @Positive long galleryId) {

        storageService.removeGalleryImage(galleryId,token);
        galleryService.deleteGallery(galleryId,token);

        return ResponseEntity.noContent().build();
    }
}
