package com.main19.server.sse.mapper;

import com.main19.server.sse.dto.SseResponseDto;
import com.main19.server.sse.entity.Sse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SseMapper {

    @Mapping(source = "receiver.memberId" , target = "memberId")
    SseResponseDto sseToSseResponseDto(Sse sse);

    List<SseResponseDto> sseToSseResponseDtos(List<Sse> sse);
}