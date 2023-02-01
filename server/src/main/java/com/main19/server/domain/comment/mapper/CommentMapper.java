package com.main19.server.domain.comment.mapper;
import com.main19.server.domain.comment.dto.CommentDto;
import com.main19.server.domain.comment.entity.Comment;
import com.main19.server.domain.comment.like.dto.CommentLikeGetResponseDto;
import com.main19.server.domain.comment.like.entity.CommentLike;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment commentsPostDtoToComments(CommentDto.Post requestBody);

    Comment commentsPatchDtoToComments(CommentDto.Patch requestBody);

    @Mapping(source = "member.userName" , target = "userName")
    @Mapping(source = "member.memberId" , target = "memberId")
    @Mapping(source = "member.profileImage" , target = "profileImage")
    @Mapping(source = "posting.postingId", target = "postingId")
    CommentDto.Response commentsToCommentsResponseDto(Comment comment);

    List<CommentDto.Response> commentsToCommentsResponseDtos(List<Comment> comments);

    @Mapping(source = "member.memberId" , target = "memberId")
    @Mapping(source = "member.userName" , target = "userName")
    CommentLikeGetResponseDto commentLikeToCommentLikeResponseGetResponseDto(CommentLike commentLike);

}
