package com.main19.server.sse.mapper;

import com.main19.server.sse.dto.SseResponseDto;
import com.main19.server.sse.entity.Sse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SseMapper {

    @Mapping(source = "sender.memberId" , target = "memberId")
    @Mapping(source = "sender.profileImage" , target = "profileImage")
    @Mapping(source = "sender.userName" , target = "userName")
    SseResponseDto sseToSseResponseDto(Sse sse);

    List<SseResponseDto> sseToSseResponseDtos(List<Sse> sse);
}
