package com.main19.server.domain.follow.mapper;

import com.main19.server.domain.follow.dto.FollowDto;
import com.main19.server.domain.follow.entity.Follow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FollowMapper {
    @Mapping(source = "followerId.memberId", target = "followerId")
    @Mapping(source = "followingId.memberId", target = "followingId")
    FollowDto.Response followToFollowResponseDto(Follow follow);
}
