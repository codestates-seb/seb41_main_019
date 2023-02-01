package com.main19.server.domain.posting.scrap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
public class ScrapDto {
    @Positive
    private long postingId;
    @Positive
    private long memberId;
}
