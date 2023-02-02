package com.main19.server.gallery;

import static com.main19.server.utils.DocumentUtils.getRequestPreProcessor;
import static com.main19.server.utils.DocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.main19.server.domain.myplants.entity.MyPlants;
import com.main19.server.domain.myplants.gallery.controller.GalleryController;
import com.main19.server.domain.myplants.gallery.dto.GalleryDto;
import com.main19.server.domain.myplants.gallery.dto.GalleryDto.Post;
import com.main19.server.domain.myplants.gallery.dto.GalleryDto.Response;
import com.main19.server.domain.myplants.gallery.entity.Gallery;
import com.main19.server.domain.myplants.gallery.mapper.GalleryMapper;
import com.main19.server.domain.myplants.gallery.service.GalleryService;
import com.main19.server.domain.myplants.service.MyPlantsService;
import com.main19.server.global.storageService.s3.GalleryStorageService;

import java.nio.charset.StandardCharsets;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(value = GalleryController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriHost = "increaf.site")
public class GalleryControllerRestDocs {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private GalleryStorageService storageService;
    @MockBean
    private GalleryMapper galleryMapper;
    @MockBean
    private GalleryService galleryService;
    @MockBean
    private MyPlantsService myPlantsService;

    @Test
    public void postGalleryTest() throws Exception {

        long myPlantsId = 1L;
        long galleryId = 1L;

        GalleryDto.Post galleryPost = new Post("안녕 난 머호");

        String content = gson.toJson(galleryPost);

        MockMultipartFile galleryImage = new MockMultipartFile("galleryImage", "Image.jpeg",
            "image/jpeg", "<<jpeg data>>".getBytes());
        MockMultipartFile requestBody = new MockMultipartFile("requestBody", "", "application/json",
            content.getBytes(
                StandardCharsets.UTF_8));

        GalleryDto.Response response = new Response(myPlantsId, galleryId, "안녕 난 머호", "plantImage",
            LocalDateTime.now());

        given(storageService.uploadGalleryImage(Mockito.any())).willReturn("imageUrl");
        given(galleryMapper.galleryDtoPostToGallery(Mockito.any())).willReturn(new Gallery());
        given(myPlantsService.findMyPlants(Mockito.anyLong())).willReturn(new MyPlants());
        given(galleryService.createGallery(Mockito.any(), Mockito.any(), Mockito.anyString(),
            Mockito.anyString())).willReturn(new Gallery());
        given(galleryMapper.galleryToGalleryDtoResponse(Mockito.any())).willReturn(response);

        ResultActions actions = mockMvc.perform(
            RestDocumentationRequestBuilders.multipart("/myplants/{myplants-id}/gallery",
                    myPlantsId)
                .file(galleryImage)
                .file(requestBody)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer AccessToken")
        );

        actions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.content").value(galleryPost.getContent()))
            .andDo(document(
                "post-gallery",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer AccessToken")
                ),
                pathParameters(
                    parameterWithName("myplants-id").description("내 식물 식별자")
                ),
                requestParts(
                    RequestDocumentation.partWithName("galleryImage").description("첨부파일"),
                    RequestDocumentation.partWithName("requestBody").description("게시글 내용")
                ),
                requestPartFields(
                    "requestBody",
                    List.of(
                        fieldWithPath("content").type(JsonFieldType.STRING).description("갤러리 내용")
                    )
                ),
                responseFields(
                    fieldWithPath("data.myPlantsId").type(JsonFieldType.NUMBER)
                        .description("내 식물 식별자"),
                    fieldWithPath("data.galleryId").type(JsonFieldType.NUMBER)
                        .description("갤러리 식별자"),
                    fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("data.plantImage").type(JsonFieldType.STRING)
                        .description("식물 이미지"),
                    fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("작성 시간")
                )
            ));
    }

    @Test
    public void getGalleryTest() throws Exception {

        long myPlantsId = 1L;
        long galleryId = 1L;

        GalleryDto.Response response = new Response(myPlantsId, galleryId, "안녕 난 머호", "plantImage",
            LocalDateTime.now());

        given(galleryService.findGallery(Mockito.anyLong())).willReturn(new Gallery());
        given(galleryMapper.galleryToGalleryDtoResponse(Mockito.any())).willReturn(response);

        ResultActions actions = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/myplants/gallery/{gallery-id}", galleryId)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer AccessToken")
        );

        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.myPlantsId").value(response.getMyPlantsId()))
            .andExpect(jsonPath("$.data.galleryId").value(response.getGalleryId()))
            .andExpect(jsonPath("$.data.content").value(response.getContent()))
            .andExpect(jsonPath("$.data.plantImage").value(response.getPlantImage()))
            .andDo(document(
                "get-gallery",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer AccessToken")
                ),
                pathParameters(
                    parameterWithName("gallery-id").description("갤러리 식별자")
                ),
                responseFields(
                    fieldWithPath("data.myPlantsId").type(JsonFieldType.NUMBER).description("내 식물 식별자"),
                    fieldWithPath("data.galleryId").type(JsonFieldType.NUMBER).description("갤러리 식별자"),
                    fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("data.plantImage").type(JsonFieldType.STRING).description("식물 이미지"),
                    fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("작성 시간")
                )
            ));
    }

    @Test
    public void getsGalleryTest() throws Exception {

        long myPlantsId = 1L;
        long galleryId1 = 1L;
        long galleryId2 = 1L;

        MyPlants myPlants = new MyPlants();
        myPlants.setMyPlantsId(myPlantsId);

        Gallery gallery1 = new Gallery(galleryId1,"안녕 난 머호","plantImage",LocalDateTime.now(),myPlants);
        Gallery gallery2 = new Gallery(galleryId1,"안녕 난 머호","plantImage",LocalDateTime.now(),myPlants);

        Page<Gallery> gallery = new PageImpl<>(List.of(gallery1, gallery2));

        GalleryDto.Response response1 = new Response(myPlantsId, galleryId1, "안녕 난 머호", "plantImage",
            LocalDateTime.now());
        GalleryDto.Response response2 = new Response(myPlantsId, galleryId2, "안녕 난 머호", "plantImage",
            LocalDateTime.now());

        List<GalleryDto.Response> response = new ArrayList<>();
        response.add(response1);
        response.add(response2);

        given(galleryService.findByAllMyPlantsId(Mockito.anyLong(),Mockito.anyInt(),Mockito.anyInt())).willReturn(gallery);
        given(galleryMapper.galleryListToGalleryResponseList(Mockito.any())).willReturn(response);

        ResultActions actions = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/myplants/{myplants-id}/gallery", myPlantsId)
                .param("page","1")
                .param("size","10")
                .accept(MediaType.APPLICATION_JSON)
        );

        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].myPlantsId").value(response.get(0).getMyPlantsId()))
            .andExpect(jsonPath("$.data[0].galleryId").value(response.get(0).getGalleryId()))
            .andExpect(jsonPath("$.data[0].content").value(response.get(0).getContent()))
            .andExpect(jsonPath("$.data[0].plantImage").value(response.get(0).getPlantImage()))
            .andExpect(jsonPath("$.data[1].myPlantsId").value(response.get(1).getMyPlantsId()))
            .andExpect(jsonPath("$.data[1].galleryId").value(response.get(1).getGalleryId()))
            .andExpect(jsonPath("$.data[1].content").value(response.get(1).getContent()))
            .andExpect(jsonPath("$.data[1].plantImage").value(response.get(1).getPlantImage()))
            .andDo(document(
                "gets-gallery",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestParameters(
                        parameterWithName("page").description("조회 할 페이지"),
                        parameterWithName("size").description("조회 할 데이터 갯수")
                ),
                pathParameters(
                    parameterWithName("myplants-id").description("내 식물 식별자")
                ),
                responseFields(
                    fieldWithPath("data[].myPlantsId").type(JsonFieldType.NUMBER).description("내 식물 식별자"),
                    fieldWithPath("data[].galleryId").type(JsonFieldType.NUMBER).description("갤러리 식별자"),
                    fieldWithPath("data[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("data[].plantImage").type(JsonFieldType.STRING).description("식물 이미지"),
                    fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("작성 시간"),
                    fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이징 정보"),
                    fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                    fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 사이즈"),
                    fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 수"),
                    fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                )
            ));
    }

    @Test
    public void deleteGalleryTest() throws Exception {

        long galleryId = 1L;

        doNothing().when(storageService).removeGalleryImage(Mockito.anyLong(), Mockito.anyString());
        doNothing().when(galleryService).deleteGallery(Mockito.anyLong(), Mockito.anyString());

        ResultActions actions =
            mockMvc.perform(
                delete("/myplants/gallery/{gallery-id}", galleryId)
                    .header("Authorization", "Bearer AccessToken")
            );

        actions.andExpect(status().isNoContent())
            .andDo(
                document(
                    "delete-gallery",
                    getRequestPreProcessor(),
                    getResponsePreProcessor(),
                    pathParameters(
                        parameterWithName("gallery-id").description("갤러리 식별자")
                    ),
                    requestHeaders(
                        headerWithName("Authorization").description("Bearer (accessToken)")
                    )
                )
            );
    }
}
