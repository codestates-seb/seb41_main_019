package com.main19.server.myplants.service;

import com.main19.server.auth.jwt.JwtTokenizer;
import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.member.service.MemberService;
import com.main19.server.myplants.entity.MyPlants;
import com.main19.server.myplants.gallery.entity.Gallery;
import com.main19.server.myplants.gallery.service.GalleryService;
import com.main19.server.myplants.repository.MyPlantsRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPlantsService {

    private final MyPlantsRepository myPlantsRepository;
    private final GalleryService galleryService;
    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;

    public MyPlants createMyPlants(MyPlants myPlants, long memberId, String token) {

        if (memberId != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        myPlants.setMember(memberService.findMember(memberId));

        return myPlantsRepository.save(myPlants);
    }

    public MyPlants changeMyPlants(long myPlantsId , long galleryId, int changeNumber, String token) {

        MyPlants findMyPlants = findVerifiedMyPlants(myPlantsId);
        Gallery findGallery = galleryService.findGallery(galleryId);

        if (findMyPlants.getMember().getMemberId() != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        for(int i = 0; i< findMyPlants.getGalleryList().size(); i++) {
            if(findMyPlants.getGalleryList().get(i).getGalleryId() == galleryId) {
                if(changeNumber > galleryId) {
                    findMyPlants.getGalleryList().add(changeNumber-1,findGallery);
                    findMyPlants.getGalleryList().remove(i);
                    break;
                }
                findMyPlants.getGalleryList().remove(i);
                findMyPlants.getGalleryList().add(changeNumber-1,findGallery);
                break;
            }
        }
        return findMyPlants;
    }
    public MyPlants findMyPlants(long myPlantsId) {
        return findVerifiedMyPlants(myPlantsId);
    }

    public Page<MyPlants> findByMyPlants(int page, int size, long memberId) {
        return myPlantsRepository.findByMember_MemberId(memberId,(PageRequest.of(page, size, Sort.by("myPlantsId").descending())));
    }

    public void deleteMyPlants(long myPlantsId, String token) {

        MyPlants findMyPlants = findVerifiedMyPlants(myPlantsId);

        if (findMyPlants.getMemberId() != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        myPlantsRepository.delete(findMyPlants);
    }

    private MyPlants findVerifiedMyPlants(long myPlantsId) {
        Optional<MyPlants> optionalMyPlants = myPlantsRepository.findById(myPlantsId);
        MyPlants findMyPlants =
            optionalMyPlants.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MYPLANTS_NOT_FOUND));
        return findMyPlants;
    }
}
