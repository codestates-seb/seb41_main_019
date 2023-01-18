package com.main19.server.follow;

import com.main19.server.auth.jwt.JwtTokenizer;
import com.main19.server.comment.controller.CommentController;
import com.main19.server.follow.controller.FollowController;
import com.main19.server.follow.dto.FollowDto;
import com.main19.server.follow.entity.Follow;
import com.main19.server.follow.mapper.FollowMapper;
import com.main19.server.follow.service.FollowService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = FollowController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
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
        long followedMemberId = 2L;
        FollowDto.Response response = new FollowDto.Response(followId, followedMemberId, followingMemberId);

        // when
        given(followService.createFollow(Mockito.anyLong(), Mockito.anyLong())).willReturn(new Follow());
        given(mapper.followToFollowResponseDto(Mockito.any(Follow.class))).willReturn(response);

        // then
        ResultActions actions = mockMvc.perform(
                post("/followings/{member-id}", followedMemberId)
                        .header("Authorization", "Bearer AccessToken")
                        .accept(MediaType.APPLICATION_JSON)
        );

        actions
                .andExpect(status().isCreated());
    }
}
