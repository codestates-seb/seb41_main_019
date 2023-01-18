package com.main19.server.myplants.gallery.service;

import com.main19.server.auth.jwt.JwtTokenizer;
import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.myplants.entity.MyPlants;
import com.main19.server.myplants.gallery.entity.Gallery;
import com.main19.server.myplants.gallery.repository.GalleryRepository;
import com.main19.server.member.entity.Member;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final JwtTokenizer jwtTokenizer;
    private final GalleryRepository galleryRepository;

    public Gallery createGallery(Gallery gallery, MyPlants myPlants, String mediaPaths, Member tokenMember) {

        if (myPlants.getMember().getMemberId() != tokenMember.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        gallery.setPlantImage(mediaPaths);
        gallery.setMyPlants(myPlants);

        return galleryRepository.save(gallery);
    }

    public Gallery findGallery(long galleryId) {
        return findVerifiedGallery(galleryId);
    }

    public List<Gallery> findByMyPlantsId(long myPlantsId) {
        return galleryRepository.findByMyPlants_MyPlantsId(myPlantsId);
    }

    public Page<Gallery> findByAllMyPlantsId(long myPlantsId, int page, int size) {
        return galleryRepository.findByMyPlants_MyPlantsId(myPlantsId, PageRequest.of(page, size, Sort.by("my_Plants_Id").descending()));
    }

    public void deleteGallery(long galleryId, Member tokenMember) {

        Gallery gallery = findVerifiedGallery(galleryId);

        if(gallery.getMyPlants().getMember().getMemberId() != tokenMember.getMemberId()){
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }
        galleryRepository.delete(gallery);
    }

    private Gallery findVerifiedGallery(long galleryId) {
        Optional<Gallery> optionalGallery = galleryRepository.findById(galleryId);
        Gallery findGallery =
            optionalGallery.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.GALLERY_NOT_FOUND));
        return findGallery;
    }
}
