package com.main19.server.sse;

import static com.main19.server.utils.DocumentUtils.getRequestPreProcessor;
import static com.main19.server.utils.DocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.main19.server.domain.member.entity.Member;
import com.main19.server.domain.posting.entity.Posting;
import com.main19.server.domain.sse.controller.SseController;
import com.main19.server.domain.sse.dto.SseResponseDto;
import com.main19.server.domain.sse.entity.Sse;
import com.main19.server.domain.sse.entity.Sse.SseType;
import com.main19.server.domain.sse.mapper.SseMapper;
import com.main19.server.domain.sse.service.SseService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(value = SseController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriHost = "increaf.site")
public class SseRestDocs {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SseService sseService;
    @MockBean
    private SseMapper sseMapper;
    @Autowired
    private Gson gson;

    @Test
    public void patchSseTest() throws Exception {

        long sseId = 1L;

        SseResponseDto response = new SseResponseDto(sseId,1L,1L,"profileImage","머호",true,"posting");

        given(sseService.updateSse(Mockito.anyLong(),Mockito.anyString())).willReturn(new Sse());
        given(sseService.findSseId(Mockito.anyLong())).willReturn(new Sse());
        given(sseMapper.sseToSseResponseDto(Mockito.any())).willReturn(response);

        ResultActions actions =
            mockMvc.perform(
                patch("/notification/{sse-id}", sseId)
                    .header("Authorization", "Bearer (accessToken)")
                    .accept(MediaType.APPLICATION_JSON)
            );


        actions
            .andExpect(status().isOk())
            .andDo(document("patch-sse",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                pathParameters(
                    parameterWithName("sse-id").description("알림 식별자")
                ),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer (accessToken)")
                ),
                responseFields(
                    List.of(
                        fieldWithPath("data.sseId").type(JsonFieldType.NUMBER).description("알림 식별자"),
                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                        fieldWithPath("data.postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                        fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("회원 이미지"),
                        fieldWithPath("data.userName").type(JsonFieldType.STRING).description("회원 닉네임"),
                        fieldWithPath("data.read").type(JsonFieldType.BOOLEAN).description("알림 확인 여부"),
                        fieldWithPath("data.sseType").type(JsonFieldType.STRING).description("알림 타입")
                    )
                )
            ));
    }

    @Test
    public void GetSseTest() throws Exception {

        long sseId1 = 1L;
        long sseId2 = 2L;

        long postingId = 1L;
        long nullPostingId = 0L;
        long memberId1 = 1L;
        long memberId2 = 2L;

        Member member1 = new Member();
        member1.setMemberId(memberId1);
        member1.setProfileImage("profileImage");
        member1.setUserName("머호");

        Member member2 = new Member();
        member2.setMemberId(memberId2);
        member2.setProfileImage("profileImage");
        member2.setUserName("머호2");

        Posting posting = new Posting();
        posting.setPostingId(postingId);

        Sse sse1 = new Sse(member1, SseType.comment, member2, false, posting);
        sse1.setSseId(sseId1);
        Sse sse2 = new Sse(member1, SseType.message, member2, false, null);
        sse2.setSseId(sseId2);

        Page<Sse> page = new PageImpl<>(List.of(sse1, sse2));

        given(sseService.findSse(Mockito.anyLong(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString()))
            .willReturn(page);

        List<Sse> list = List.of(sse1,sse2);

        given(sseMapper.sseToSseResponseDtos(Mockito.anyList())).willReturn(
            List.of(
                new SseResponseDto(
                    list.get(0).getSseId(),
                    list.get(0).getSender().getMemberId(),
                    list.get(0).getPosting().getPostingId(),
                    list.get(0).getSender().getProfileImage(),
                    list.get(0).getSender().getUserName(),
                    list.get(0).isRead(),
                    list.get(0).getSseType().toString()
                    ),
                new SseResponseDto(
                    list.get(1).getSseId(),
                    list.get(1).getSender().getMemberId(),
                    nullPostingId,
                    list.get(1).getSender().getProfileImage(),
                    list.get(1).getSender().getUserName(),
                    list.get(1).isRead(),
                    list.get(1).getSseType().toString())));

        ResultActions actions =
            mockMvc.perform(
                get("/notification/{member-id}", memberId1)
                    .header("Authorization", "Bearer (accessToken)")
                    .param("page","1")
                    .param("size","10")
                    .accept(MediaType.APPLICATION_JSON)
            );

        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].sseId").value(list.get(0).getSseId()))
            .andExpect(jsonPath("$.data[0].memberId").value(list.get(0).getSender().getMemberId()))
            .andExpect(jsonPath("$.data[0].postingId").value(list.get(0).getPosting().getPostingId()))
            .andExpect(jsonPath("$.data[0].profileImage").value(list.get(0).getSender().getProfileImage()))
            .andExpect(jsonPath("$.data[0].userName").value(list.get(0).getSender().getUserName()))
            .andExpect(jsonPath("$.data[0].read").value( list.get(0).isRead()))
            .andExpect(jsonPath("$.data[0].sseType").value(list.get(0).getSseType().toString()))
            .andExpect(jsonPath("$.data[1].sseId").value(list.get(1).getSseId()))
            .andExpect(jsonPath("$.data[1].memberId").value(list.get(1).getSender().getMemberId()))
            .andExpect(jsonPath("$.data[1].postingId").value(nullPostingId))
            .andExpect(jsonPath("$.data[1].profileImage").value(list.get(1).getSender().getProfileImage()))
            .andExpect(jsonPath("$.data[1].userName").value(list.get(1).getSender().getUserName()))
            .andExpect(jsonPath("$.data[1].read").value( list.get(1).isRead()))
            .andExpect(jsonPath("$.data[1].sseType").value(list.get(1).getSseType().toString()))
            .andDo(document(
                "get-sse",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer AccessToken")
                ),
                pathParameters(
                    parameterWithName("member-id").description("회원 식별자")
                ),
                requestParameters(
                    parameterWithName("page").description("조회 할 페이지"),
                    parameterWithName("size").description("조회 할 데이터 갯수")
                ),
                responseFields(
                    fieldWithPath("data[].sseId").type(JsonFieldType.NUMBER).description("알림 식별자"),
                    fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("data[].postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                    fieldWithPath("data[].profileImage").type(JsonFieldType.STRING).description("회원 이미지"),
                    fieldWithPath("data[].userName").type(JsonFieldType.STRING).description("회원 닉네임"),
                    fieldWithPath("data[].read").type(JsonFieldType.BOOLEAN).description("알림 확인 여부"),
                    fieldWithPath("data[].sseType").type(JsonFieldType.STRING).description("알림 타입"),
                    fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이징 정보"),
                    fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                    fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 사이즈"),
                    fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 수"),
                    fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                )
            ));
    }

    @Test
    public void patchAllSseTest() throws Exception {

        doNothing().when(sseService).updateAllSse(Mockito.anyString());

        ResultActions resultActions = mockMvc.perform(
            patch("/notification")
                .header("Authorization", "Bearer (accessToken)")
        );

        resultActions.andExpect(status().isOk())
            .andDo(
                document(
                    "patch-all-sse",
                    getRequestPreProcessor(),
                    getResponsePreProcessor(),
                    requestHeaders(
                        headerWithName("Authorization").description("Bearer AccessToken")
                    )
                )
            );
    }

    @Test
    public void deleteSseTest() throws Exception {

        long sseId = 1L;

        long postingId = 1L;
        long memberId1 = 1L;
        long memberId2 = 2L;

        Member member1 = new Member();
        member1.setMemberId(memberId1);
        member1.setProfileImage("profileImage");
        member1.setUserName("머호");

        Member member2 = new Member();
        member2.setMemberId(memberId2);
        member2.setProfileImage("profileImage");
        member2.setUserName("머호2");

        Posting posting = new Posting();
        posting.setPostingId(postingId);

        Sse sse = new Sse(member1, SseType.comment, member2, false, posting);
        sse.setSseId(sseId);

        doNothing().when(sseService).deleteSee(sseId,"token");

        ResultActions resultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.delete("/notification/{sse-id}" , sseId)
                .header("Authorization", "Bearer (accessToken)")
        );

        resultActions.andExpect(status().isNoContent())
            .andDo(
                document(
                    "delete-sse",
                    getRequestPreProcessor(),
                    getResponsePreProcessor(),
                    requestHeaders(
                        headerWithName("Authorization").description("Bearer AccessToken")
                    ),
                    pathParameters(
                        parameterWithName("sse-id").description("알림 식별자")
                    )
                )
            );
    }
}
