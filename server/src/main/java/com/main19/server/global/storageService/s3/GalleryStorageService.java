package com.main19.server.global.storageService.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.main19.server.domain.myplants.gallery.repository.GalleryRepository;
import com.main19.server.global.auth.jwt.JwtTokenizer;
import com.main19.server.global.exception.BusinessLogicException;
import com.main19.server.global.exception.ExceptionCode;
import com.main19.server.domain.myplants.entity.MyPlants;
import com.main19.server.domain.myplants.gallery.entity.Gallery;
import com.main19.server.domain.myplants.gallery.service.GalleryService;
import com.main19.server.domain.myplants.service.MyPlantsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GalleryStorageService extends S3StorageService{
    private final GalleryRepository galleryRepository;
    private final AmazonS3 s3Client;
    private final JwtTokenizer jwtTokenizer;

    public String uploadGalleryImage(MultipartFile galleryImage) {
        String galleryImageUrl;

        String fileName = createFileName(galleryImage.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(galleryImage.getSize());
        objectMetadata.setContentType(galleryImage.getContentType());

        try(InputStream inputStream = galleryImage.getInputStream()) {
            s3Client.putObject(new PutObjectRequest(bucket + "/gallery/plantImage", fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            galleryImageUrl = (s3Client.getUrl(bucket + "/gallery/plantImage", fileName).toString());
        } catch(IOException e) {
            throw new BusinessLogicException(ExceptionCode.MEDIA_UPLOAD_ERROR);
        }
        return  galleryImageUrl;
    }


    public void removeGalleryImage(Gallery gallery, String token) {


        if (gallery.getMyPlantsMemberId() != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        String fileName = (gallery.getPlantImage()).substring(73);

        if (!s3Client.doesObjectExist(bucket + "/gallery/plantImage", fileName)) {
            throw new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND);
        }
        s3Client.deleteObject(bucket + "/gallery/plantImage", fileName);
    }

    public void removeAllGalleryImage(MyPlants myPlants,String token) {

        if (myPlants.getMemberId() != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        List<Gallery> findGallery = galleryRepository.findByMyPlants_MyPlantsId(myPlants.getMyPlantsId());

        for(int i= 0; i<findGallery.size(); i++) {
            String fileName = findGallery.get(i).getPlantImage().substring(73);

            if (!s3Client.doesObjectExist(bucket + "/gallery/plantImage", fileName)) {
                throw new BusinessLogicException(ExceptionCode.MEDIA_NOT_FOUND);
            }
            s3Client.deleteObject(bucket + "/gallery/plantImage", fileName);
        }
    }


}
