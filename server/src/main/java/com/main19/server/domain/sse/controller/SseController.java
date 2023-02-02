package com.main19.server.domain.sse.controller;

import com.main19.server.global.dto.MultiResponseDto;
import com.main19.server.global.dto.SingleResponseDto;
import com.main19.server.domain.sse.dto.SseResponseDto;
import com.main19.server.domain.sse.entity.Sse;
import com.main19.server.domain.sse.mapper.SseMapper;
import com.main19.server.domain.sse.service.SseService;
import java.util.List;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class SseController {

    private final SseService sseService;
    private final SseMapper sseMapper;

    @GetMapping(value = "/notification/subscribe/{id}", produces = "text/event-stream; charset=UTF-8")
    public SseEmitter subscribe(@RequestHeader(name = "Authorization") String token, @PathVariable Long id,
        @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return sseService.subscribe(id, lastEventId, token);
    }

    @PatchMapping("/notification/{sse-id}")
    public ResponseEntity patchSse(@RequestHeader(name = "Authorization") String token,
        @PathVariable("sse-id") @Positive long sseId) {

        sseService.updateSse(sseId, token);
        SseResponseDto response = sseMapper.sseToSseResponseDto(sseService.findSseId(sseId));

        return new ResponseEntity(new SingleResponseDto<>(response),HttpStatus.OK);
    }

    @PatchMapping("/notification")
    public ResponseEntity patchAllSse(@RequestHeader(name = "Authorization") String token) {

        sseService.updateAllSse(token);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/notification/{member-id}")
    public ResponseEntity getSse(@RequestHeader(name = "Authorization") String token, @PathVariable("member-id") @Positive long memberId,
        @Positive @RequestParam int page, @Positive @RequestParam int size) {

        Page<Sse> pageSse = sseService.findSse(memberId,page-1,size,token);
        List<Sse> response = pageSse.getContent();

        return new ResponseEntity<>(
            new MultiResponseDto<>(sseMapper.sseToSseResponseDtos(response),
                pageSse), HttpStatus.OK);
    }

    @DeleteMapping("/notification/{sse-id}")
    public ResponseEntity deleteSee(@RequestHeader(name = "Authorization") String token,
        @PathVariable("sse-id") @Positive long sseId) {

        sseService.deleteSee(sseId,token);

        return ResponseEntity.noContent().build();
    }
}
