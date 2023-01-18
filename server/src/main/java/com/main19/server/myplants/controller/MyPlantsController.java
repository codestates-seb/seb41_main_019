package com.main19.server.myplants.controller;

import com.main19.server.auth.Login;
import com.main19.server.dto.MultiResponseDto;
import com.main19.server.dto.SingleResponseDto;
import com.main19.server.member.entity.Member;
import com.main19.server.myplants.dto.MyPlantsDto;
import com.main19.server.myplants.entity.MyPlants;
import com.main19.server.myplants.mapper.MyPlantsMapper;
import com.main19.server.myplants.service.MyPlantsService;
import com.main19.server.s3service.S3StorageService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPlantsController {

    private final MyPlantsMapper myPlantsMapper;
    private final MyPlantsService myPlantsService;
    private final S3StorageService storageService;

    @PostMapping("/myplants")
    public ResponseEntity postMyPlants(@Login Member tokenMember,
        @Valid @RequestBody MyPlantsDto.Post requestBody) {

        MyPlants myPlants = myPlantsMapper.myPlantsPostDtoToMyPlants(requestBody);

        MyPlants createdMyPlants = myPlantsService.createMyPlants(myPlants, requestBody.getMemberId(), tokenMember);

        MyPlantsDto.Response response = myPlantsMapper.myPlantsToMyPlantsResponseDto(createdMyPlants);

        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @PatchMapping("/myplants/{myplants-id}")
    public ResponseEntity patchMyPlants(@Login Member tokenMember, @PathVariable("myplants-id") @Positive long myPlantsId,
        @Valid @RequestBody MyPlantsDto.Patch requestBody) {

        MyPlants myPlants = myPlantsService.changeMyPlants(myPlantsId, requestBody.getGalleryId(),
            requestBody.getChangeNumber(), tokenMember);

        MyPlantsDto.Response response = myPlantsMapper.myPlantsToMyPlantsResponseDto(myPlants);

        return new ResponseEntity(new SingleResponseDto<>(response),HttpStatus.OK);
    }

    @GetMapping("/{member-id}/myplants")
    public ResponseEntity getsMyPlants(@PathVariable("member-id") @Positive long memberId,
        @RequestParam @Positive int page, @RequestParam @Positive int size) {

        Page<MyPlants> myPlantsPage = myPlantsService.findByMyPlants(page-1,size,memberId);
        List<MyPlants> content = myPlantsPage.getContent();
        List<MyPlantsDto.Response> response = myPlantsMapper.myPlantsListToMyPlantsResponseDto(content);

        return new ResponseEntity(new MultiResponseDto<>(response,myPlantsPage),HttpStatus.OK);
    }

    @GetMapping("/myplants/{myplants-id}")
    public ResponseEntity getMyPlants(@PathVariable("myplants-id") @Positive long myPlantsId) {

        MyPlants myPlants = myPlantsService.findMyPlants(myPlantsId);
        MyPlantsDto.Response response = myPlantsMapper.myPlantsToMyPlantsResponseDto(myPlants);

        return new ResponseEntity(new SingleResponseDto<>(response),HttpStatus.OK);
    }

    @DeleteMapping("/myplants/{myplants-id}")
    public ResponseEntity deleteMyPlants(@Login Member tokenMember, @PathVariable("myplants-id") @Positive long myPlantsId) {

        storageService.removeAllGalleryImage(myPlantsId,tokenMember);
        myPlantsService.deleteMyPlants(myPlantsId,tokenMember);

        return ResponseEntity.noContent().build();
    }
}
