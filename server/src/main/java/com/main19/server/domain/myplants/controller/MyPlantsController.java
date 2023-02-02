package com.main19.server.domain.myplants.controller;

import com.main19.server.global.dto.MultiResponseDto;
import com.main19.server.global.dto.SingleResponseDto;
import com.main19.server.domain.myplants.dto.MyPlantsDto;
import com.main19.server.domain.myplants.entity.MyPlants;
import com.main19.server.domain.myplants.mapper.MyPlantsMapper;
import com.main19.server.domain.myplants.service.MyPlantsService;
import com.main19.server.global.storageService.s3.GalleryStorageService;

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
    private final GalleryStorageService storageService;

    @PostMapping("/myplants")
    public ResponseEntity postMyPlants(@RequestHeader(name = "Authorization") String token,
        @Valid @RequestBody MyPlantsDto.Post requestBody) {

        MyPlants myPlants = myPlantsMapper.myPlantsPostDtoToMyPlants(requestBody);

        MyPlants createdMyPlants = myPlantsService.createMyPlants(myPlants, requestBody.getMemberId(), token);

        MyPlantsDto.Response response = myPlantsMapper.myPlantsToMyPlantsResponseDto(createdMyPlants);

        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @PatchMapping("/myplants/{myplants-id}")
    public ResponseEntity patchMyPlant(@RequestHeader(name = "Authorization") String token, @PathVariable("myplants-id") @Positive long myPlantsId,
        @Valid @RequestBody MyPlantsDto.PlantsPatch requestBody) {

        requestBody.setMyPlantsId(myPlantsId);

        MyPlants myPlants = myPlantsMapper.myPlantsPatchDtoToMyPlants(requestBody);

        MyPlants updateMyPlants = myPlantsService.updateMyPlants(myPlants,token);

        MyPlantsDto.Response response = myPlantsMapper.myPlantsToMyPlantsResponseDto(updateMyPlants);

        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @PatchMapping("/myplants/{myplants-id}/gallerys")
    public ResponseEntity patchMyPlants(@RequestHeader(name = "Authorization") String token, @PathVariable("myplants-id") @Positive long myPlantsId,
        @Valid @RequestBody MyPlantsDto.Patch requestBody) {

        MyPlants myPlants = myPlantsService.changeMyPlants(myPlantsId, requestBody.getGalleryId(),
            requestBody.getChangeNumber(), token);

        MyPlantsDto.Response response = myPlantsMapper.myPlantsToMyPlantsResponseDto(myPlants);

        return new ResponseEntity(new SingleResponseDto<>(response),HttpStatus.OK);
    }

    @GetMapping("/{member-id}/myplants")
    public ResponseEntity getsMyPlants(@PathVariable("member-id") @Positive long memberId, @RequestParam @Positive int page, @RequestParam @Positive int size) {

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
    public ResponseEntity deleteMyPlants(@RequestHeader(name = "Authorization") String token, @PathVariable("myplants-id") @Positive long myPlantsId) {

        storageService.removeAllGalleryImage(myPlantsId,token);
        myPlantsService.deleteMyPlants(myPlantsId,token);

        return ResponseEntity.noContent().build();
    }
}
