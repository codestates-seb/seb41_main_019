package com.main19.server.sse;

import static com.main19.server.utils.DocumentUtils.getRequestPreProcessor;
import static com.main19.server.utils.DocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.main19.server.sse.controller.SseController;
import com.main19.server.sse.dto.SseResponseDto;
import com.main19.server.sse.entity.Sse;
import com.main19.server.sse.mapper.SseMapper;
import com.main19.server.sse.service.SseService;
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

@WebMvcTest(value = SseController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
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
}
