package com.main19.server.domain.posting.scrap.controller;

import com.main19.server.domain.posting.mapper.PostingMapper;
import com.main19.server.domain.posting.scrap.dto.ScrapDto;
import com.main19.server.global.dto.SingleResponseDto;
import com.main19.server.domain.posting.scrap.entity.Scrap;
import com.main19.server.domain.posting.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/scraps")
@Validated
@Slf4j
@RequiredArgsConstructor
public class ScrapController {
    private final ScrapService scrapService;
    private final PostingMapper mapper;

    @PostMapping("/{posting-id}")
    public ResponseEntity scrapPosting(@RequestHeader(name = "Authorization") String token,
                                       @PathVariable("posting-id") @Positive long postingId,
                                       @RequestBody ScrapDto requestBody) {
        Scrap scrap = scrapService.createScrap(mapper.scrapDtoToScrap(requestBody), postingId, requestBody.getMemberId(), token);

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.scrapToScrapPostResponseDto(scrap)) , HttpStatus.CREATED);
    }

    @DeleteMapping("/{scrap-id}")
    public ResponseEntity scrapDelete(@RequestHeader(name = "Authorization") String token,
                                      @PathVariable("scrap-id") @Positive long scrapId) {
        scrapService.deleteScrap(scrapId, token);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
