package com.main19.server.posting.dto;

import com.main19.server.comment.dto.CommentDto;
import com.main19.server.posting.like.dto.PostingLikeGetResponseDto;
import com.main19.server.posting.tags.dto.PostingTagsResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostingAllResponseDto {
    private long postingId;
    private long memberId;
    private String userName;
    private String profileImage;
    private String postingContent;
    private List<MediaResponseDto> postingMedias;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<PostingTagsResponseDto> tags;
    private long likeCount;
    private long commentCount;
    private boolean isScrapped; // posting 과 member 간 스크랩 테이블 하나 더 필요
}
