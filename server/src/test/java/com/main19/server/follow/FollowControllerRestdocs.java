package com.main19.server.follow;

import com.main19.server.global.auth.jwt.JwtTokenizer;
import com.main19.server.domain.follow.controller.FollowController;
import com.main19.server.domain.follow.dto.FollowDto;
import com.main19.server.domain.follow.entity.Follow;
import com.main19.server.domain.follow.mapper.FollowMapper;
import com.main19.server.domain.follow.service.FollowService;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = FollowController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriHost = "increaf.site")
public class FollowControllerRestdocs {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FollowService followService;
    @MockBean
    private FollowMapper mapper;
    @MockBean
    private JwtTokenizer jwtTokenizer;

    @Test
    public void PostFollowTest() throws Exception {
        // given
        long followId = 1L;
        long followingMemberId = 1L;
        long followerMemberId = 2L;
        FollowDto.Response response = new FollowDto.Response(followId, followerMemberId, followingMemberId);

        // when
        given(followService.createFollow(Mockito.anyString(), Mockito.anyLong())).willReturn(new Follow());
        given(mapper.followToFollowResponseDto(Mockito.any(Follow.class))).willReturn(response);

        // then
        ResultActions actions = mockMvc.perform(
                post("/followings/{member-id}", followId)
                        .header("Authorization", "Bearer AccessToken")
                        .accept(MediaType.APPLICATION_JSON)
        );

        actions
                .andExpect(status().isCreated())
                .andDo(document(
                        "post-follow",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        pathParameters(
                                parameterWithName("member-id").description("내가 팔로우 할 Member 식별자")
                        ),
                        responseFields(
                                fieldWithPath("data.followId").type(JsonFieldType.NUMBER).description("팔로우 식별자"),
                                fieldWithPath("data.followerId").type(JsonFieldType.NUMBER).description("팔로워 식별자"),
                                fieldWithPath("data.followingId").type(JsonFieldType.NUMBER).description("팔로잉 식별자")
                        )
                ));
    }

    @Test
    public void deleteFollowingTest() throws Exception {
        // given
        long followId = 1L;
        String token = "AccessToken";

        // when
        doNothing().when(followService).deleteFollowing(token, followId);

        // then
        ResultActions actions = mockMvc.perform(
                delete("/followings/{follow-id}", followId)
                        .header("Authorization", "Bearer AccessToken")
        );

        actions.andExpect(status().isNoContent())
                .andDo(document(
                        "delete-following",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(parameterWithName("follow-id").description("팔로우 식별자")),
                        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken"))
                ));
    }

    @Test
    public void deleteFollowedTest() throws Exception {
        // given
        String token = "AccessToken";
        long followId = 1L;

        // when
        doNothing().when(followService).deleteFollowed(token, followId);

        // then
        ResultActions actions = mockMvc.perform(
                delete("/followed/{follow-id}", followId)
                        .header("Authorization", "Bearer AccessToken")
        );

        actions.andExpect(status().isNoContent())
                .andDo(document(
                        "delete-followed",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(parameterWithName("follow-id").description("팔로우 식별자")),
                        requestHeaders(headerWithName("Authorization").description("Bearer AccessToken"))
                ));
    }
}
