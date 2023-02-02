package com.main19.server.posting;

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
import com.main19.server.domain.posting.like.controller.PostingLikeController;
import com.main19.server.domain.posting.like.dto.PostingLikeDto;
import com.main19.server.domain.posting.like.dto.PostingLikeResponseDto;
import com.main19.server.domain.posting.like.entity.PostingLike;
import com.main19.server.domain.posting.like.service.PostingLikeService;
import com.main19.server.domain.posting.mapper.PostingMapper;
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


@WebMvcTest(value = PostingLikeController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriHost = "increaf.site")
public class PostingLikeControllerRestDocs {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private PostingLikeService postingLikeService;
    @MockBean
    private PostingMapper postingMapper;

    @Test
    public void postPostingLikeTest() throws Exception {

        long postingLikeId = 1L;
        long postingId = 1L;
        long memberId = 1L;

        PostingLikeDto post = new PostingLikeDto(memberId);

        String content = gson.toJson(post);

        PostingLikeResponseDto response = new PostingLikeResponseDto(postingLikeId,postingId,memberId);

        given(postingLikeService.createPostingLike(Mockito.anyLong(),
                Mockito.anyLong(), Mockito.anyString())).willReturn(new PostingLike());
        given(postingMapper.postingLikeToPostingLikeResponseDto(Mockito.any(PostingLike.class))).willReturn(response);

        ResultActions actions =
            mockMvc.perform(
                post("/posts/{posting-id}/likes", postingId)
                    .header("Authorization", "Bearer AccessToken")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.memberId").value(post.getMemberId()))
            .andDo(document(
                "post-posting-like",
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
                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자")
                    )
                ),
                responseFields(
                    fieldWithPath("data.postingLikeId").type(JsonFieldType.NUMBER).description("좋아요 식별자"),
                    fieldWithPath("data.postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                    fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자")
                )
            ));
    }

    @Test
    public void deletePostingLikeTest() throws Exception {

        long postingLikeId = 1L;

        doNothing().when(postingLikeService).deletePostingLike(Mockito.anyLong(),Mockito.anyString());

        ResultActions actions =
            mockMvc.perform(
                delete("/posts/likes/{posting-likes-id}", postingLikeId)
                    .header("Authorization", "Bearer AccessToken")
            );

        actions
            .andExpect(status().isNoContent())
            .andDo(document(
                "delete-posting-like",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer AccessToken")
                ),
                pathParameters(
                    parameterWithName("posting-likes-id").description("좋아요 식별자")
                ))
            );
    }
}
