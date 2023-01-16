package com.main19.server.follow.controller;

import com.main19.server.follow.dto.FollowDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/follows")
public class FollowController {
    @PostMapping("/member-id")
    public ResponseEntity postFollow(@PathVariable("{member-id}") @Positive long memberId,
                                     @RequestHeader(name = "Authorization") String token,
                                     @RequestBody FollowDto.Post requestBody) {
        return null;

    }
}
