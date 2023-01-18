package com.main19.server.comment;

import static com.main19.server.utils.DocumentUtils.getRequestPreProcessor;
import static com.main19.server.utils.DocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.main19.server.auth.LoginUserArgumentResolver;
import com.main19.server.comment.controller.CommentController;
import com.main19.server.comment.dto.CommentDto;
import com.main19.server.comment.dto.CommentDto.Patch;
import com.main19.server.comment.dto.CommentDto.Post;
import com.main19.server.comment.entity.Comment;
import com.main19.server.comment.mapper.CommentMapper;
import com.main19.server.comment.service.CommentService;
import com.main19.server.member.entity.Member;
import com.main19.server.posting.entity.Posting;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(value = CommentController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class CommentControllerRestDocs {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;
    @MockBean
    private CommentMapper commentMapper;
    @Autowired
    private Gson gson;
    @MockBean
    private LoginUserArgumentResolver loginUserArgumentResolver;

    @Test
    public void PostCommentTest() throws Exception {

        long postingId = 1L;
        long memberId = 1L;
        long commentId = 1L;

        CommentDto.Post post = new Post(memberId, "댓글 test");

        String content = gson.toJson(post);

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime modifiedAt = LocalDateTime.now();

        CommentDto.Response response =
            new CommentDto.Response(
                commentId,
                postingId,
                memberId,
                "oheadnah",
                "image",
                "댓글 test",
                0L,
                new ArrayList<>(),
                createdAt,
                modifiedAt);

        given(commentMapper.commentsPostDtoToComments(Mockito.any(CommentDto.Post.class)))
            .willReturn(new Comment());

        given(commentService.createComment(Mockito.any(Comment.class), Mockito.anyLong(),
            Mockito.anyLong(), Mockito.any()))
            .willReturn(new Comment());

        given(commentMapper.commentsToCommentsResponseDto(Mockito.any(Comment.class))).willReturn(
            response);

        ResultActions actions =
            mockMvc.perform(
                post("/comments/{posting-id}", postingId)
                    .header("Authorization", "Bearer AccessToken")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.memberId").value(post.getMemberId()))
            .andExpect(jsonPath("$.data.comment").value(post.getComment()))
            .andDo(document(
                "post-comment",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer AccessToken")
                ),
                pathParameters(
                    parameterWithName("posting-id").description("게시글 식별자")
                ),
                requestFields(
                    List.of(
                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                        fieldWithPath("comment").type(JsonFieldType.STRING).description("댓글 내용")
                    )
                ),
                responseFields(
                    fieldWithPath("data.commentId").type(JsonFieldType.NUMBER).description("댓글 식별자"),
                    fieldWithPath("data.postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                    fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("data.userName").type(JsonFieldType.STRING).description("회원 닉네임"),
                    fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("회원 이미지"),
                    fieldWithPath("data.comment").type(JsonFieldType.STRING).description("댓글 내용"),
                    fieldWithPath("data.likeCount").type(JsonFieldType.NUMBER).description("좋아요 합계"),
                    fieldWithPath("data.commentLikeList").type(JsonFieldType.ARRAY).description("좋아요 누른 사람"),
                    fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("작성일"),
                    fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("최종 수정일")
                )
            ));
    }

    @Test
    public void patchCommentTest() throws Exception {

        long postingId = 1L;
        long memberId = 1L;
        long commentId = 1L;

        CommentDto.Patch patch = new Patch(commentId, "댓글 수정 test");

        String content = gson.toJson(patch);

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime modifiedAt = LocalDateTime.now();

        CommentDto.Response response =
            new CommentDto.Response(
                commentId,
                postingId,
                memberId,
                "oheadnah",
                "image",
                "댓글 수정 test",
                0L,
                new ArrayList<>(),
                createdAt,
                modifiedAt);

        given(commentMapper.commentsPatchDtoToComments(Mockito.any(CommentDto.Patch.class)))
            .willReturn(new Comment());

        given(commentService.updateComment(Mockito.any(Comment.class), Mockito.any()))
            .willReturn(new Comment());

        given(commentMapper.commentsToCommentsResponseDto(Mockito.any(Comment.class))).willReturn(
            response);

        ResultActions actions =
            mockMvc.perform(
                patch("/comments/{comment-id}", commentId)
                    .header("Authorization", "Bearer AccessToken")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.commentId").value(patch.getCommentId()))
            .andExpect(jsonPath("$.data.comment").value(patch.getComment()))
            .andDo(document(
                "patch-comment",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer AccessToken")
                ),
                pathParameters(
                    parameterWithName("comment-id").description("댓글 식별자")
                ),
                requestFields(
                    List.of(
                        fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("댓글 식별자"),
                        fieldWithPath("comment").type(JsonFieldType.STRING).description("댓글 내용")
                    )
                ),
                responseFields(
                    fieldWithPath("data.commentId").type(JsonFieldType.NUMBER).description("댓글 식별자"),
                    fieldWithPath("data.postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                    fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("data.userName").type(JsonFieldType.STRING).description("회원 닉네임"),
                    fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("회원 이미지"),
                    fieldWithPath("data.comment").type(JsonFieldType.STRING).description("댓글 내용"),
                    fieldWithPath("data.likeCount").type(JsonFieldType.NUMBER).description("좋아요 합계"),
                    fieldWithPath("data.commentLikeList").type(JsonFieldType.ARRAY).description("좋아요 누른 사람"),
                    fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("작성일"),
                    fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("최종 수정일")
                )
            ));
    }

    @Test
    public void deleteCommentTest() throws Exception {

        long commentId = 1L;

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime modifiedAt = LocalDateTime.now();

        Member member = new Member();
        member.setMemberId(1L);

        Posting posting = new Posting();

        Comment comment = new Comment(commentId, "댓글 삭제 test", createdAt, modifiedAt, 0L, posting, member, new ArrayList<>());

        doNothing().when(commentService).deleteComment(comment.getCommentId(), member);

        ResultActions actions =
            mockMvc.perform(
                delete("/comments/{comment-id}", commentId)
                    .header("Authorization", "Bearer AccessToken")
            );

        actions.andExpect(status().isNoContent())
            .andDo(
                document(
                    "delete-comment",
                    getRequestPreProcessor(),
                    getResponsePreProcessor(),
                    pathParameters(parameterWithName("comment-id").description("댓글 식별자")
                    ),
                    requestHeaders(
                        headerWithName("Authorization").description("Bearer (accessToken)")
                    )
                )
            );
    }
}
