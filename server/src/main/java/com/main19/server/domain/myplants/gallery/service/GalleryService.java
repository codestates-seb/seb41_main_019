package com.main19.server.domain.myplants.gallery.service;

import com.main19.server.global.auth.jwt.JwtTokenizer;
import com.main19.server.domain.myplants.gallery.repository.GalleryRepository;
import com.main19.server.global.exception.BusinessLogicException;
import com.main19.server.global.exception.ExceptionCode;
import com.main19.server.domain.myplants.entity.MyPlants;
import com.main19.server.domain.myplants.gallery.entity.Gallery;

import java.util.List;
import java.util.Optional;

import com.main19.server.global.storageService.s3.GalleryStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class GalleryService {

    private final JwtTokenizer jwtTokenizer;
    private final GalleryRepository galleryRepository;
    private final GalleryStorageService storageService;


    public Gallery createGallery(Gallery gallery, MyPlants myPlants, MultipartFile galleryImage, String token) {

        if (myPlants.getMemberId() != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        String imagePath = storageService.uploadGalleryImage(galleryImage);
        gallery.setPlantImage(imagePath);
        gallery.setMyPlants(myPlants);

        return galleryRepository.save(gallery);
    }

    @Transactional(readOnly = true)
    public Gallery findGallery(long galleryId) {
        return findVerifiedGallery(galleryId);
    }

    @Transactional(readOnly = true)
    public List<Gallery> findByMyPlantsId(long myPlantsId) {
        return galleryRepository.findByMyPlants_MyPlantsId(myPlantsId);
    }

    @Transactional(readOnly = true)
    public Page<Gallery> findByAllMyPlantsId(long myPlantsId, int page, int size) {
        return galleryRepository.findByMyPlants_MyPlantsId(myPlantsId, PageRequest.of(page, size, Sort.by("my_Plants_Id").descending()));
    }

    public void deleteGallery(long galleryId, String token) {

        Gallery gallery = findVerifiedGallery(galleryId);

        if(gallery.getMyPlantsMemberId() != jwtTokenizer.getMemberId(token)){
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }
        storageService.removeGalleryImage(gallery,token);
        galleryRepository.delete(gallery);
    }

    @Transactional(readOnly = true)
    private Gallery findVerifiedGallery(long galleryId) {
        Optional<Gallery> optionalGallery = galleryRepository.findById(galleryId);
        Gallery findGallery =
                optionalGallery.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.GALLERY_NOT_FOUND));
        return findGallery;
    }
}
