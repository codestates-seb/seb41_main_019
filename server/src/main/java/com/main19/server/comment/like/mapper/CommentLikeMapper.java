package com.main19.server.comment.like.mapper;

import com.main19.server.comment.like.dto.CommentLikeDto;
import com.main19.server.comment.like.dto.CommentLikeGetResponseDto;
import com.main19.server.comment.like.entity.CommentLike;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CommentLikeMapper {
    @Mapping(source = "member.memberId" , target = "memberId")
    @Mapping(source = "comment.commentId" , target = "commentId")
    CommentLikeDto.Response commentLikeToCommentLikeResponse(CommentLike commentLike);

}
