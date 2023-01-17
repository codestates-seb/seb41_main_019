package com.main19.server.myplants;

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
import com.main19.server.myplants.controller.MyPlantsController;
import com.main19.server.myplants.dto.MyPlantsDto;
import com.main19.server.myplants.dto.MyPlantsDto.Patch;
import com.main19.server.myplants.dto.MyPlantsDto.Post;
import com.main19.server.myplants.dto.MyPlantsDto.Response;
import com.main19.server.myplants.entity.MyPlants;
import com.main19.server.myplants.gallery.dto.GalleryDto;
import com.main19.server.myplants.mapper.MyPlantsMapper;
import com.main19.server.myplants.service.MyPlantsService;
import com.main19.server.s3service.S3StorageService;
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

@WebMvcTest(value = MyPlantsController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MyPlantsControllerRestDocs {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private MyPlantsMapper myPlantsMapper;
    @MockBean
    private MyPlantsService myPlantsService;
    @MockBean
    private S3StorageService storageService;


    @Test
    public void PostMyPlantsTest() throws Exception {

        long myPlantsId = 1L;
        long memberId = 1L;

        MyPlantsDto.Post post = new Post(memberId, "머호");

        String content = gson.toJson(post);

        MyPlantsDto.Response response = new Response(myPlantsId, "머호", new ArrayList<>());

        given(myPlantsMapper.myPlantsPostDtoToMyPlants(Mockito.any(MyPlantsDto.Post.class)))
            .willReturn(new MyPlants());

        given(myPlantsService.createMyPlants(Mockito.any(MyPlants.class), Mockito.anyLong(),
            Mockito.anyString()))
            .willReturn(new MyPlants());

        given(myPlantsMapper.myPlantsToMyPlantsResponseDto(Mockito.any(MyPlants.class))).willReturn(
            response);

        ResultActions actions =
            mockMvc.perform(
                post("/myplants")
                    .header("Authorization", "Bearer AccessToken")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.plantName").value(post.getPlantName()))
            .andDo(document(
                "post-my-plants",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer AccessToken")
                ),
                requestFields(
                    List.of(
                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                        fieldWithPath("plantName").type(JsonFieldType.STRING).description("식물 이름")
                    )
                ),
                responseFields(
                    fieldWithPath("data.myPlantsId").type(JsonFieldType.NUMBER).description("내 식물 식별자"),
                    fieldWithPath("data.plantName").type(JsonFieldType.STRING).description("식물 이름"),
                    fieldWithPath("data.galleryList").type(JsonFieldType.ARRAY).description("갤러리 리스트")
                )
            ));
    }

    @Test
    public void patchMyPlantsTest() throws Exception {

        long myPlantsId = 1L;
        long galleryId1 = 1L;
        long galleryId2 = 2L;
        int changeNumber = 2;

        MyPlantsDto.Patch patch = new Patch(galleryId1, changeNumber);

        GalleryDto.MyPlantsResponse response1 = new GalleryDto.MyPlantsResponse(galleryId1);
        GalleryDto.MyPlantsResponse response2 = new GalleryDto.MyPlantsResponse(galleryId2);

        List<GalleryDto.MyPlantsResponse> responses = new ArrayList<>();
        responses.add(response2);
        responses.add(response1);

        String content = gson.toJson(patch);

        MyPlantsDto.Response response = new Response(myPlantsId, "머호", responses);

        given(myPlantsService.changeMyPlants(Mockito.anyLong(), Mockito.anyLong(),
            Mockito.anyInt(), Mockito.anyString()))
            .willReturn(new MyPlants());

        given(myPlantsMapper.myPlantsToMyPlantsResponseDto(Mockito.any(MyPlants.class))).willReturn(
            response);

        ResultActions actions =
            mockMvc.perform(
                patch("/myplants/{myplants-id}", myPlantsId)
                    .header("Authorization", "Bearer AccessToken")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.galleryList[1].galleryId").value(patch.getGalleryId()))
            .andDo(document(
                "patch-my-plants",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer AccessToken")
                ),
                pathParameters(
                    parameterWithName("myplants-id").description("내 식물 식별자")
                ),
                requestFields(
                    List.of(
                        fieldWithPath("galleryId").type(JsonFieldType.NUMBER).description("갤러리 식별자"),
                        fieldWithPath("changeNumber").type(JsonFieldType.NUMBER).description("이동시킬 위치")
                    )
                ),
                responseFields(
                    fieldWithPath("data.myPlantsId").type(JsonFieldType.NUMBER).description("내 식물 식별자"),
                    fieldWithPath("data.plantName").type(JsonFieldType.STRING).description("식물 이름"),
                    fieldWithPath("data.galleryList[].galleryId").type(JsonFieldType.NUMBER).description("갤러리 식별자")
                )
            ));
    }

        @Test
        public void deleteMyPlantsTest() throws Exception {

            long myPlantsId = 1L;

            doNothing().when(storageService).removeAllGalleryImage(Mockito.anyLong(),Mockito.anyString());
            doNothing().when(myPlantsService).deleteMyPlants(Mockito.anyLong(),Mockito.anyString());

            ResultActions actions =
                mockMvc.perform(
                    delete("/myplants/{myplants-id}", myPlantsId)
                        .header("Authorization", "Bearer AccessToken")
                );

            actions.andExpect(status().isNoContent())
                .andDo(
                    document(
                        "delete-my-plants",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                            parameterWithName("myplants-id").description("내 식물 식별자")
                        ),
                        requestHeaders(
                            headerWithName("Authorization").description("Bearer (accessToken)")
                        )
                    )
                );
    }
}
