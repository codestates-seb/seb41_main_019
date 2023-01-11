package com.main19.server.posting.scrap.controller;

import com.main19.server.dto.SingleResponseDto;
import com.main19.server.posting.mapper.PostingMapper;
import com.main19.server.posting.scrap.dto.ScrapDto;
import com.main19.server.posting.scrap.entity.Scrap;
import com.main19.server.posting.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/scrap")
@Validated
@Slf4j
@RequiredArgsConstructor
public class ScrapController {
    private final ScrapService scrapService;
    private final PostingMapper mapper;

    @PostMapping("/{posting-id}")
    public ResponseEntity scrapPosting(@PathVariable("posting-id") @Positive long postingId,
                                       @RequestBody ScrapDto requestBody) {
        Scrap scrap = scrapService.createScrap(mapper.scrapDtoToScrap(requestBody), postingId, requestBody.getMemberId());

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.scrapToScrapResponseDto(scrap)) , HttpStatus.CREATED);
    }

    @DeleteMapping("/{scrap-id}")
    public ResponseEntity scrapDelete(@PathVariable("scrap-id") @Positive long scrapId) {
        scrapService.deleteScrap(scrapId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
