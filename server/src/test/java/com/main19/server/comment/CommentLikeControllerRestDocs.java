package com.main19.server.comment;

import static com.main19.server.utils.DocumentUtils.getRequestPreProcessor;
import static com.main19.server.utils.DocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.main19.server.domain.comment.like.controller.CommentLikeController;
import com.main19.server.domain.comment.like.dto.CommentLikeDto;
import com.main19.server.domain.comment.like.dto.CommentLikeDto.Post;
import com.main19.server.domain.comment.like.dto.CommentLikeDto.Response;
import com.main19.server.domain.comment.like.entity.CommentLike;
import com.main19.server.domain.comment.like.mapper.CommentLikeMapper;
import com.main19.server.domain.comment.like.service.CommentLikeService;

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

@WebMvcTest(value = CommentLikeController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriHost = "increaf.site")
public class CommentLikeControllerRestDocs {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private CommentLikeService commentLikeService;
    @MockBean
    private CommentLikeMapper commentLikeMapper;

    @Test
    public void postCommentLikeTest() throws Exception {

        long commentLikeId = 1L;
        long commentId = 1L;
        long memberId = 1L;

        CommentLikeDto.Post post = new Post(memberId);

        String content = gson.toJson(post);

        CommentLikeDto.Response response = new Response(commentLikeId,memberId,commentId);

        given(commentLikeService.createLike(Mockito.anyLong(),
                Mockito.anyLong(), Mockito.anyString())).willReturn(new CommentLike());

        given(commentLikeMapper.commentLikeToCommentLikeResponse(
            Mockito.any(CommentLike.class))).willReturn(response);

        ResultActions actions =
            mockMvc.perform(
                post("/comments/{comment-id}/likes", commentId)
                    .header("Authorization", "Bearer AccessToken")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.memberId").value(post.getMemberId()))
            .andDo(document(
                "post-comment-like",
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
                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자")
                    )
                ),
                responseFields(
                    fieldWithPath("data.commentLikeId").type(JsonFieldType.NUMBER).description("좋아요 식별자"),
                    fieldWithPath("data.commentId").type(JsonFieldType.NUMBER).description("댓글 식별자"),
                    fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자")
                )
            ));
    }

    @Test
    public void deletePostingLikeTest() throws Exception {

        long commentLikeId = 1L;

        doNothing().when(commentLikeService)
            .deleteLike(Mockito.anyLong(), Mockito.anyString());

        ResultActions actions =
            mockMvc.perform(
                delete("/comments/likes/{comment-likes-id}", commentLikeId)
                    .header("Authorization", "Bearer AccessToken")
            );

        actions
            .andExpect(status().isNoContent())
            .andDo(document(
                "delete-comment-like",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer AccessToken")
                ),
                pathParameters(
                    parameterWithName("comment-likes-id").description("좋아요 식별자")
                ))
            );
    }
}
