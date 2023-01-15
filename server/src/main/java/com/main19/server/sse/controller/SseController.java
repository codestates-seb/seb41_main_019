package com.main19.server.sse.controller;

import com.main19.server.dto.MultiResponseDto;
import com.main19.server.dto.SingleResponseDto;
import com.main19.server.sse.dto.SseResponseDto;
import com.main19.server.sse.entity.Sse;
import com.main19.server.sse.mapper.SseMapper;
import com.main19.server.sse.service.SseService;
import java.util.List;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class SseController {

    private final SseService sseService;
    private final SseMapper sseMapper;

    @GetMapping(value = "/subscribe/{id}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable Long id,
        @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return sseService.subscribe(id, lastEventId);
    }

    @PatchMapping("/notification/{sse-id}")
    public ResponseEntity patchSse(@RequestHeader(name = "Authorization") String token,
        @PathVariable("sse-id") @Positive long sseId) {

        sseService.updateSse(sseId, token);
        SseResponseDto response = sseMapper.sseToSseResponseDto(sseService.findSseId(sseId));

        return new ResponseEntity(new SingleResponseDto<>(response),HttpStatus.OK);
    }

    @GetMapping("/notification/{member-id}")
    public ResponseEntity getSse(@PathVariable("member-id") @Positive long memberId,
        @PageableDefault(size = 10, sort = "sse_id", direction = Direction.ASC) Pageable pageable) {

        Page<Sse> pageSse = sseService.findSse(memberId,pageable);
        List<Sse> response = pageSse.getContent();

        return new ResponseEntity<>(
            new MultiResponseDto<>(sseMapper.sseToSseResponseDtos(response),
                pageSse), HttpStatus.OK);
    }

    @DeleteMapping("/notification/{member-id}")
    public ResponseEntity deleteSee(@RequestHeader(name = "Authorization") String token,
        @PathVariable("sse-id") @Positive long sseId) {

        sseService.deleteSee(sseId,token);

        return ResponseEntity.noContent().build();
    }
}
