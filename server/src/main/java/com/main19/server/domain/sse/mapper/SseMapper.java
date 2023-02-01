package com.main19.server.domain.sse.mapper;

import com.main19.server.domain.sse.dto.SseResponseDto;
import com.main19.server.domain.sse.entity.Sse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SseMapper {

    @Mapping(source = "sender.memberId" , target = "memberId")
    @Mapping(source = "sender.profileImage" , target = "profileImage")
    @Mapping(source = "sender.userName" , target = "userName")
    @Mapping(source = "posting.postingId" , target = "postingId")
    @Mapping(source = "read" , target = "isRead")
    SseResponseDto sseToSseResponseDto(Sse sse);

    List<SseResponseDto> sseToSseResponseDtos(List<Sse> sse);
}
