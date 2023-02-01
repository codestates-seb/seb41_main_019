package com.main19.server.posting;

import com.google.gson.Gson;
import com.main19.server.domain.posting.mapper.PostingMapper;
import com.main19.server.domain.posting.scrap.controller.ScrapController;
import com.main19.server.domain.posting.scrap.dto.ScrapDto;
import com.main19.server.domain.posting.scrap.dto.ScrapPostResponseDto;
import com.main19.server.domain.posting.scrap.entity.Scrap;
import com.main19.server.domain.posting.scrap.service.ScrapService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.main19.server.utils.DocumentUtils.getRequestPreProcessor;
import static com.main19.server.utils.DocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ScrapController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriHost = "increaf.site")
public class ScrapControllerRestDocs {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private ScrapService scrapService;
    @MockBean
    private PostingMapper mapper;

    @Test
    public void postScrapTest() throws Exception {
        // given
        long scrapId = 1L;
        long postingId = 1L;
        long memberId = 1L;

        ScrapDto scrap = new ScrapDto(postingId, memberId);

        String content = gson.toJson(scrap);

        ScrapPostResponseDto response = new ScrapPostResponseDto(scrapId, postingId, memberId);

        given(mapper.scrapDtoToScrap(Mockito.any(ScrapDto.class))).willReturn(new Scrap());
        given(scrapService.createScrap(Mockito.any(Scrap.class), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString())).willReturn(new Scrap());
        given(mapper.scrapToScrapPostResponseDto(Mockito.any(Scrap.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.post("/scraps/{posting-id}", postingId)
                        .header("Authorization", "Bearer AccessToken")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.postingId").value(scrap.getPostingId()))
                .andExpect(jsonPath("$.data.memberId").value(scrap.getMemberId()))
                .andDo(document(
                        "post-scrap",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        pathParameters(
                                parameterWithName("posting-id").description("게시글 식별자")
                        ),
                        requestFields(
                                List.of(fieldWithPath("postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자")
                                )
                        ),
                        responseFields(
                                fieldWithPath("data.scrapId").type(JsonFieldType.NUMBER).description("스크랩 식별자"),
                                fieldWithPath("data.postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자")
                        )
                ));
    }

    @Test
    public void deleteScrapTest() throws Exception {
        // given
        long scrapId = 1L;

        doNothing().when(scrapService).deleteScrap(Mockito.anyLong(), Mockito.anyString());

        // when
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/scraps/{scrap-id}", scrapId)
                                .header("Authorization", "Bearer AccessToken")
                );

        // then
        actions.andExpect(status().isNoContent())
                .andDo(
                        document(
                                "delete-scrap",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(parameterWithName("scrap-id").description("스크랩 식별자")
                                ),
                                requestHeaders(
                                        headerWithName("Authorization").description("Bearer (accessToken)")
                                )
                        ));
    }
}
