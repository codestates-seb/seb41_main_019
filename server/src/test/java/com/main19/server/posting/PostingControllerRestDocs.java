package com.main19.server.posting;

import com.google.gson.Gson;
import com.main19.server.domain.member.entity.Member;
import com.main19.server.domain.posting.controller.PostingController;
import com.main19.server.domain.posting.dto.MediaResponseDto;
import com.main19.server.domain.posting.dto.PostingPatchDto;
import com.main19.server.domain.posting.dto.PostingPostDto;
import com.main19.server.domain.posting.dto.PostingResponseDto;
import com.main19.server.domain.posting.entity.Media;
import com.main19.server.domain.posting.entity.Posting;
import com.main19.server.domain.posting.mapper.PostingMapper;
import com.main19.server.domain.posting.service.PostingService;
import com.main19.server.domain.posting.tags.dto.PostingTagsResponseDto;

import com.main19.server.domain.posting.tags.entity.PostingTags;
import com.main19.server.domain.posting.tags.entity.Tag;
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

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.main19.server.utils.DocumentUtils.getRequestPreProcessor;
import static com.main19.server.utils.DocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = PostingController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriHost = "increaf.site")
public class PostingControllerRestDocs {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private PostingService postingService;
    @MockBean
    private PostingMapper mapper;

    @Test
    public void postPostingTest() throws Exception {
        // given
        long postingId = 1L;
        long memberId = 1L;
        String postingContent = "게시글 test";
        List<String> tagName = new ArrayList<>();
        tagName.add("스투키");
        tagName.add("몬스테라");

        PostingPostDto post = new PostingPostDto(memberId, postingContent, tagName);

        PostingTagsResponseDto tag1 = new PostingTagsResponseDto("스투키");
        PostingTagsResponseDto tag2 = new PostingTagsResponseDto("몬스테라");

        List<PostingTagsResponseDto> tags = new ArrayList<>();
        tags.add(tag1);
        tags.add(tag2);

        String content = gson.toJson(post);

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime modifiedAt = LocalDateTime.now();

        // multipart/form-data
        MockMultipartFile file1 = new MockMultipartFile("file1", "Image.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("file2", "Image.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());

        MediaResponseDto response1 = new MediaResponseDto(1L,"imageUrl", "", "image");
        MediaResponseDto response2 = new MediaResponseDto(2L,"imageUrl", "thumbnail", "video");

        List<MediaResponseDto> responseList = new ArrayList<>();
        responseList.add(response1);
        responseList.add(response2);

        MockMultipartFile requestBody = new MockMultipartFile("requestBody", "", "application/json", content.getBytes(StandardCharsets.UTF_8));

        PostingResponseDto response =
                new PostingResponseDto(
                        postingId,
                        memberId,
                        "gimhae_person",
                        "image",
                        "게시글 test",
                        responseList,
                        createdAt,
                        modifiedAt,
                        tags,
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>()
                );


        given(postingService.createPosting(Mockito.any(PostingPostDto.class), Mockito.anyLong(), Mockito.anyList(), Mockito.anyString())).willReturn(new Posting());
        given(mapper.postingToPostingResponseDto(Mockito.any(Posting.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.multipart("/posts")
                        .file(file1)
                        .file(file2)
                        .file(requestBody)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("Authorization", "Bearer AccessToken")
        );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.memberId").value(post.getMemberId()))
                .andExpect(jsonPath("$.data.postingContent").value(post.getPostingContent()))
                .andExpect(jsonPath("$.data.tags[0].tagName").value(post.getTagName().get(0)))
                .andExpect(jsonPath("$.data.tags[1].tagName").value(post.getTagName().get(1)))
                .andDo(document(
                        "post-posting",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        requestParts(
                                RequestDocumentation.partWithName("file1").description("첨부파일1"),
                                RequestDocumentation.partWithName("file2").optional().description("첨부파일2"),
                                RequestDocumentation.partWithName("file3").optional().description("첨부파일3"),
                                RequestDocumentation.partWithName("requestBody").description("게시글 내용")
                        ),
                        requestPartFields(
                                "requestBody",
                                List.of(fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("postingContent").type(JsonFieldType.STRING).description("게시글 내용"),
                                        fieldWithPath("tagName").type(JsonFieldType.ARRAY).description("태그 이름")
                                )
                        ),
                        responseFields(
                                fieldWithPath("data.postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("data.userName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("회원 이미지"),
                                fieldWithPath("data.postingContent").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.postingMedias[].mediaId").type(JsonFieldType.NUMBER).description("첨부파일 식별자"),
                                fieldWithPath("data.postingMedias[].mediaUrl").type(JsonFieldType.STRING).description("첨부파일 주소"),
                                fieldWithPath("data.postingMedias[].thumbnailUrl").type(JsonFieldType.STRING).description("동영상 썸네일 이미지 주소"),
                                fieldWithPath("data.postingMedias[].format").type(JsonFieldType.STRING).description("첨부파일 형식"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("작성일"),
                                fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("최종 수정일"),
                                fieldWithPath("data.tags[].tagName").type(JsonFieldType.STRING).description("태그 이름"),
                                fieldWithPath("data.likeCount").type(JsonFieldType.NUMBER).description("좋아요 합계"),
                                fieldWithPath("data.postingLikes").type(JsonFieldType.ARRAY).description("좋아요 누른 회원 리스트"),
                                fieldWithPath("data.commentCount").type(JsonFieldType.NUMBER).description("댓글 합계"),
                                fieldWithPath("data.comments").type(JsonFieldType.ARRAY).description("댓글 작성한 회원 리스르"),
                                fieldWithPath("data.scrapMemberList").type(JsonFieldType.ARRAY).description("해당 게시글을 스크랩한 회원 리스트")
                        )
                ));
    }

    @Test
    public void patchPostingTest() throws Exception {
        // given
        long postingId = 1L;
        String postingContent = "게시글 수정 test";
        List<String> tagName = new ArrayList<>();
        tagName.add("스투키");
        tagName.add("몬스테라");

        PostingPatchDto patch = new PostingPatchDto(postingContent, tagName);

        PostingTagsResponseDto tag1 = new PostingTagsResponseDto("스투키");
        PostingTagsResponseDto tag2 = new PostingTagsResponseDto("몬스테라");

        List<PostingTagsResponseDto> tags = new ArrayList<>();
        tags.add(tag1);
        tags.add(tag2);

        MediaResponseDto response1 = new MediaResponseDto(1L,"imageUrl", "", "image");
        MediaResponseDto response2 = new MediaResponseDto(2L,"imageUrl", "thumbnail", "video");

        List<MediaResponseDto> responseList = new ArrayList<>();
        responseList.add(response1);
        responseList.add(response2);

        String content = gson.toJson(patch);

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime modifiedAt = LocalDateTime.now();

        PostingResponseDto response =
                new PostingResponseDto(
                        postingId,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 수정 test",
                        responseList,
                        createdAt,
                        modifiedAt,
                        tags,
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>()
                );

        given(postingService.updatePosting(Mockito.anyLong(), Mockito.any(PostingPatchDto.class), Mockito.anyString())).willReturn(new Posting());
        given(mapper.postingToPostingResponseDto(Mockito.any(Posting.class))).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.patch("/posts/{posting-id}", postingId)
                                .header("Authorization", "Bearer AccessToken")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.postingContent").value(patch.getPostingContent()))
                .andExpect(jsonPath("$.data.tags[0].tagName").value(patch.getTagName().get(0)))
                .andExpect(jsonPath("$.data.tags[1].tagName").value(patch.getTagName().get(1)))
                .andDo(document(
                        "patch-posting",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        pathParameters(
                                parameterWithName("posting-id").description("게시글 식별자")
                        ),
                        requestFields(
                                List.of(fieldWithPath("postingContent").type(JsonFieldType.STRING).description("게시글 내용"),
                                        fieldWithPath("tagName").type(JsonFieldType.ARRAY).description("태그 이름")
                                )
                        ),
                        responseFields(
                                fieldWithPath("data.postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("data.userName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("회원 이미지"),
                                fieldWithPath("data.postingContent").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.postingMedias[].mediaId").type(JsonFieldType.NUMBER).description("첨부파일 식별자"),
                                fieldWithPath("data.postingMedias[].mediaUrl").type(JsonFieldType.STRING).description("첨부파일 주소"),
                                fieldWithPath("data.postingMedias[].thumbnailUrl").type(JsonFieldType.STRING).description("동영상 썸네일 이미지 주소"),
                                fieldWithPath("data.postingMedias[].format").type(JsonFieldType.STRING).description("첨부파일 형식"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("작성일"),
                                fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("최종 수정일"),
                                fieldWithPath("data.tags[].tagName").type(JsonFieldType.STRING).description("태그 이름"),
                                fieldWithPath("data.likeCount").type(JsonFieldType.NUMBER).description("좋아요 합계"),
                                fieldWithPath("data.postingLikes").type(JsonFieldType.ARRAY).description("좋아요 누른 회원 리스트"),
                                fieldWithPath("data.commentCount").type(JsonFieldType.NUMBER).description("댓글 합계"),
                                fieldWithPath("data.comments").type(JsonFieldType.ARRAY).description("댓글 작성한 회원 리스르"),
                                fieldWithPath("data.scrapMemberList").type(JsonFieldType.ARRAY).description("해당 게시글을 스크랩한 회원 리스트")
                        )
                ));
    }

    @Test
    public void getPostingTest() throws Exception {
        // given

        MediaResponseDto response1 = new MediaResponseDto(1L,"imageUrl", "", "image");
        MediaResponseDto response2 = new MediaResponseDto(2L,"imageUrl", "thumbnail", "video");

        List<MediaResponseDto> responseList = new ArrayList<>();
        responseList.add(response1);
        responseList.add(response2);

        long postingId = 1L;
        PostingResponseDto response =
                new PostingResponseDto(
                        postingId,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 test",
                        responseList,
                        LocalDateTime.of(2023,01,01,23,59,59),
                        LocalDateTime.of(2023,01,01,23,59,59),
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>()
                );

        given(postingService.findPosting(Mockito.anyLong())).willReturn(new Posting());
        given(mapper.postingToPostingResponseDto(Mockito.any(Posting.class))).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/posts/{posting-id}", postingId)
                                .header("Authorization", "Bearer AccessToken")
                                .accept(MediaType.APPLICATION_JSON)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.postingId").value(response.getPostingId()))
                .andExpect(jsonPath("$.data.memberId").value(response.getMemberId()))
                .andExpect(jsonPath("$.data.userName").value(response.getUserName()))
                .andExpect(jsonPath("$.data.profileImage").value(response.getProfileImage()))
                .andExpect(jsonPath("$.data.postingContent").value(response.getPostingContent()))
                .andExpect(jsonPath("$.data.createdAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data.modifiedAt").value("2023-01-01T23:59:59"))
                .andDo(document(
                        "get-single-posting",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        pathParameters(
                                parameterWithName("posting-id").description("게시글 식별자")
                        ),
                        responseFields(
                                fieldWithPath("data.postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("data.userName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("회원 이미지"),
                                fieldWithPath("data.postingContent").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.postingMedias[].mediaId").type(JsonFieldType.NUMBER).description("첨부파일 식별자"),
                                fieldWithPath("data.postingMedias[].mediaUrl").type(JsonFieldType.STRING).description("첨부파일 주소"),
                                fieldWithPath("data.postingMedias[].thumbnailUrl").type(JsonFieldType.STRING).description("동영상 썸네일 이미지 주소"),
                                fieldWithPath("data.postingMedias[].format").type(JsonFieldType.STRING).description("첨부파일 형식"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("작성일"),
                                fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("최종 수정일"),
                                fieldWithPath("data.tags[]").type(JsonFieldType.ARRAY).description("태그 이름"),
                                fieldWithPath("data.likeCount").type(JsonFieldType.NUMBER).description("좋아요 합계"),
                                fieldWithPath("data.postingLikes").type(JsonFieldType.ARRAY).description("좋아요 누른 회원 리스트"),
                                fieldWithPath("data.commentCount").type(JsonFieldType.NUMBER).description("댓글 합계"),
                                fieldWithPath("data.comments").type(JsonFieldType.ARRAY).description("댓글 작성한 회원 리스르"),
                                fieldWithPath("data.scrapMemberList").type(JsonFieldType.ARRAY).description("해당 게시글을 스크랩한 회원 리스트")
                        )
                ));
    }
    
    @Test
    public void getPostingsTest() throws Exception {
        // given
        Member member1 = new Member();
        member1.setMemberId(1L);
        member1.setUserName("gimhae_person");
        member1.setProfileImage("image");

        Member member2 = new Member();
        member2.setMemberId(2L);
        member2.setUserName("taebong98");
        member2.setProfileImage("image");

        Media media1 = new Media(1L, "imageUrl", "", "image", new Posting());
        Media media2 = new Media(2L,"imageUrl", "thumbnail", "video" , new Posting());
        List<Media> mediaList1 = new ArrayList<>();
        mediaList1.add(media1);
        mediaList1.add(media2);

        Media media3 = new Media(1L, "imageUrl", "", "image", new Posting());
        Media media4 = new Media(2L,"imageUrl", "thumbnail", "video" , new Posting());
        List<Media> mediaList2 = new ArrayList<>();
        mediaList2.add(media3);
        mediaList2.add(media4);

        MediaResponseDto response1 = new MediaResponseDto(1L, "imageUrl", "", "image");
        MediaResponseDto response2 = new MediaResponseDto(2L,"imageUrl", "thumbnail", "video");

        List<MediaResponseDto> responseList1 = new ArrayList<>();
        responseList1.add(response1);
        responseList1.add(response2);

        MediaResponseDto response3 = new MediaResponseDto(3L,"imageUrl", "", "image");
        MediaResponseDto response4 = new MediaResponseDto(4L,"imageUrl", "thumbnail", "video");

        List<MediaResponseDto> responseList2 = new ArrayList<>();
        responseList2.add(response3);
        responseList2.add(response4);

        Posting posting1 = new Posting(
                1L,
                "게시글 test1",
                mediaList1,
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member1,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Posting posting2 = new Posting(
                2L,
                "게시글 test2",
                mediaList2,
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member2,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Page<Posting> pagePostings =
                new PageImpl<>(List.of(posting1, posting2));

        List<PostingResponseDto> responses = List.of(
                new PostingResponseDto(
                        1L,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 test1",
                        responseList1,
                        LocalDateTime.of(2023,01,01,23,59,59),
                        LocalDateTime.of(2023,01,01,23,59,59),
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>()),
                new PostingResponseDto(
                        2L,
                        2L,
                        "taebong98",
                        "image",
                        "게시글 test2",
                        responseList2,
                        LocalDateTime.of(2023,01,01,23,59,59),
                        LocalDateTime.of(2023,01,01,23,59,59),
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>())
        );

        given(postingService.findPostings(Mockito.anyInt(), Mockito.anyInt())).willReturn(pagePostings);
        given(mapper.postingsToPostingsResponseDto(Mockito.anyList())).willReturn(responses);

        // when
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/posts")
                                .header("Authorization", "Bearer AccessToken")
                                .param("page", "1")
                                .param("size", "10")
                                .accept(MediaType.APPLICATION_JSON)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].postingId").value(posting1.getPostingId()))
                .andExpect(jsonPath("$.data[0].memberId").value(posting1.getMember().getMemberId()))
                .andExpect(jsonPath("$.data[0].userName").value(posting1.getMember().getUserName()))
                .andExpect(jsonPath("$.data[0].profileImage").value(posting1.getMember().getProfileImage()))
                .andExpect(jsonPath("$.data[0].postingContent").value(posting1.getPostingContent()))
                .andExpect(jsonPath("$.data[0].createdAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[0].modifiedAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[1].postingId").value(posting2.getPostingId()))
                .andExpect(jsonPath("$.data[1].memberId").value(posting2.getMember().getMemberId()))
                .andExpect(jsonPath("$.data[1].userName").value(posting2.getMember().getUserName()))
                .andExpect(jsonPath("$.data[1].profileImage").value(posting2.getMember().getProfileImage()))
                .andExpect(jsonPath("$.data[1].postingContent").value(posting2.getPostingContent()))
                .andExpect(jsonPath("$.data[1].createdAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[1].modifiedAt").value("2023-01-01T23:59:59"))
                .andDo(document(
                        "get-all-postings",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        requestParameters(
                                parameterWithName("page").description("조회 할 페이지"),
                                parameterWithName("size").description("조회 할 데이터 갯수")
                        ),
                        responseFields(
                                fieldWithPath("data[].postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("data[].userName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("data[].profileImage").type(JsonFieldType.STRING).description("회원 이미지"),
                                fieldWithPath("data[].postingContent").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data[].postingMedias[].mediaId").type(JsonFieldType.NUMBER).description("첨부파일 식별자"),
                                fieldWithPath("data[].postingMedias[].mediaUrl").type(JsonFieldType.STRING).description("첨부파일 주소"),
                                fieldWithPath("data[].postingMedias[].thumbnailUrl").type(JsonFieldType.STRING).description("동영상 썸네일 이미지 주소"),
                                fieldWithPath("data[].postingMedias[].format").type(JsonFieldType.STRING).description("첨부파일 형식"),
                                fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("작성일"),
                                fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("최종 수정일"),
                                fieldWithPath("data[].tags[]").type(JsonFieldType.ARRAY).description("태그 이름"),
                                fieldWithPath("data[].likeCount").type(JsonFieldType.NUMBER).description("좋아요 합계"),
                                fieldWithPath("data[].postingLikes").type(JsonFieldType.ARRAY).description("좋아요 누른 회원 리스트"),
                                fieldWithPath("data[].commentCount").type(JsonFieldType.NUMBER).description("댓글 합계"),
                                fieldWithPath("data[].comments").type(JsonFieldType.ARRAY).description("댓글 작성한 회원 리스르"),
                                fieldWithPath("data[].scrapMemberList").type(JsonFieldType.ARRAY).description("해당 게시글을 스크랩한 회원 리스트"),
                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이징 정보"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 사이즈"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                        )
                ));
    }

    @Test
    public void getPostingsByFollwingMemberTest() throws Exception {
        // given
        Member member = new Member();
        member.setMemberId(1L);
        member.setUserName("gimhae_person");
        member.setProfileImage("image");

        Media media1 = new Media(1L, "imageUrl", "", "image", new Posting());
        Media media2 = new Media(2L,"imageUrl", "thumbnail", "video" , new Posting());
        List<Media> mediaList1 = new ArrayList<>();
        mediaList1.add(media1);
        mediaList1.add(media2);

        Media media3 = new Media(1L, "imageUrl", "", "image", new Posting());
        Media media4 = new Media(2L,"imageUrl", "thumbnail", "video" , new Posting());
        List<Media> mediaList2 = new ArrayList<>();
        mediaList2.add(media3);
        mediaList2.add(media4);

        MediaResponseDto response1 = new MediaResponseDto(1L, "imageUrl", "", "image");
        MediaResponseDto response2 = new MediaResponseDto(2L,"imageUrl", "thumbnail", "video");

        List<MediaResponseDto> responseList1 = new ArrayList<>();
        responseList1.add(response1);
        responseList1.add(response2);

        MediaResponseDto response3 = new MediaResponseDto(3L,"imageUrl", "", "image");
        MediaResponseDto response4 = new MediaResponseDto(4L,"imageUrl", "thumbnail", "video");

        List<MediaResponseDto> responseList2 = new ArrayList<>();
        responseList2.add(response3);
        responseList2.add(response4);

        Posting posting1 = new Posting(
                1L,
                "게시글 test1",
                mediaList1,
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Posting posting2 = new Posting(
                2L,
                "게시글 test2",
                mediaList2,
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Page<Posting> pagePostings =
                new PageImpl<>(List.of(posting1, posting2));

        List<PostingResponseDto> responses = List.of(
                new PostingResponseDto(
                        1L,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 test1",
                        responseList1,
                        LocalDateTime.of(2023,01,01,23,59,59),
                        LocalDateTime.of(2023,01,01,23,59,59),
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>()),
                new PostingResponseDto(
                        2L,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 test2",
                        responseList2,
                        LocalDateTime.of(2023,01,01,23,59,59),
                        LocalDateTime.of(2023,01,01,23,59,59),
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>())
        );

        given(postingService.findPostingsByFollowing(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString())).willReturn(pagePostings);
        given(mapper.postingsToPostingsResponseDto(Mockito.anyList())).willReturn(responses);

        // when
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/posts/follow")
                                .header("Authorization", "Bearer AccessToken")
                                .param("page", "1")
                                .param("size", "10")
                                .accept(MediaType.APPLICATION_JSON)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].postingId").value(posting1.getPostingId()))
                .andExpect(jsonPath("$.data[0].memberId").value(posting1.getMember().getMemberId()))
                .andExpect(jsonPath("$.data[0].userName").value(posting1.getMember().getUserName()))
                .andExpect(jsonPath("$.data[0].profileImage").value(posting1.getMember().getProfileImage()))
                .andExpect(jsonPath("$.data[0].postingContent").value(posting1.getPostingContent()))
                .andExpect(jsonPath("$.data[0].createdAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[0].modifiedAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[1].postingId").value(posting2.getPostingId()))
                .andExpect(jsonPath("$.data[1].memberId").value(posting2.getMember().getMemberId()))
                .andExpect(jsonPath("$.data[1].userName").value(posting2.getMember().getUserName()))
                .andExpect(jsonPath("$.data[1].profileImage").value(posting2.getMember().getProfileImage()))
                .andExpect(jsonPath("$.data[1].postingContent").value(posting2.getPostingContent()))
                .andExpect(jsonPath("$.data[1].createdAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[1].modifiedAt").value("2023-01-01T23:59:59"))
                .andDo(document(
                        "get-following-postings",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        requestParameters(
                                parameterWithName("page").description("조회 할 페이지"),
                                parameterWithName("size").description("조회 할 데이터 갯수")
                        ),
                        responseFields(
                                fieldWithPath("data[].postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("data[].userName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("data[].profileImage").type(JsonFieldType.STRING).description("회원 이미지"),
                                fieldWithPath("data[].postingContent").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data[].postingMedias[].mediaId").type(JsonFieldType.NUMBER).description("첨부파일 식별자"),
                                fieldWithPath("data[].postingMedias[].mediaUrl").type(JsonFieldType.STRING).description("첨부파일 주소"),
                                fieldWithPath("data[].postingMedias[].thumbnailUrl").type(JsonFieldType.STRING).description("동영상 썸네일 이미지 주소"),
                                fieldWithPath("data[].postingMedias[].format").type(JsonFieldType.STRING).description("첨부파일 형식"),
                                fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("작성일"),
                                fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("최종 수정일"),
                                fieldWithPath("data[].tags[]").type(JsonFieldType.ARRAY).description("태그 이름"),
                                fieldWithPath("data[].likeCount").type(JsonFieldType.NUMBER).description("좋아요 합계"),
                                fieldWithPath("data[].postingLikes").type(JsonFieldType.ARRAY).description("좋아요 누른 회원 리스트"),
                                fieldWithPath("data[].commentCount").type(JsonFieldType.NUMBER).description("댓글 합계"),
                                fieldWithPath("data[].comments").type(JsonFieldType.ARRAY).description("댓글 작성한 회원 리스르"),
                                fieldWithPath("data[].scrapMemberList").type(JsonFieldType.ARRAY).description("해당 게시글을 스크랩한 회원 리스트"),
                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이징 정보"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 사이즈"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                        )
                ));
    }

    @Test
    public void getPostingsByMemberTest() throws Exception {
        // given
        Member member = new Member();
        member.setMemberId(1L);
        member.setUserName("gimhae_person");
        member.setProfileImage("image");

        Media media1 = new Media(1L, "imageUrl", "", "image", new Posting());
        Media media2 = new Media(2L,"imageUrl", "thumbnail", "video" , new Posting());
        List<Media> mediaList1 = new ArrayList<>();
        mediaList1.add(media1);
        mediaList1.add(media2);

        Media media3 = new Media(1L, "imageUrl", "", "image", new Posting());
        Media media4 = new Media(2L,"imageUrl", "thumbnail", "video" , new Posting());
        List<Media> mediaList2 = new ArrayList<>();
        mediaList2.add(media3);
        mediaList2.add(media4);

        MediaResponseDto response1 = new MediaResponseDto(1L, "imageUrl", "", "image");
        MediaResponseDto response2 = new MediaResponseDto(2L,"imageUrl", "thumbnail", "video");

        List<MediaResponseDto> responseList1 = new ArrayList<>();
        responseList1.add(response1);
        responseList1.add(response2);

        MediaResponseDto response3 = new MediaResponseDto(3L,"imageUrl", "", "image");
        MediaResponseDto response4 = new MediaResponseDto(4L,"imageUrl", "thumbnail", "video");

        List<MediaResponseDto> responseList2 = new ArrayList<>();
        responseList2.add(response3);
        responseList2.add(response4);

        Posting posting1 = new Posting(
                1L,
                "게시글 test1",
                mediaList1,
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Posting posting2 = new Posting(
                2L,
                "게시글 test2",
                mediaList2,
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Page<Posting> pagePostings =
                new PageImpl<>(List.of(posting1, posting2));

        List<PostingResponseDto> responses = List.of(
                new PostingResponseDto(
                        1L,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 test1",
                        responseList1,
                        LocalDateTime.of(2023,01,01,23,59,59),
                        LocalDateTime.of(2023,01,01,23,59,59),
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>()),
                new PostingResponseDto(
                        2L,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 test2",
                        responseList2,
                        LocalDateTime.of(2023,01,01,23,59,59),
                        LocalDateTime.of(2023,01,01,23,59,59),
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>())
        );

        given(postingService.findPostingsByMemberId(Mockito.anyLong() ,Mockito.anyInt(), Mockito.anyInt())).willReturn(pagePostings);
        given(mapper.postingsToPostingsResponseDto(Mockito.anyList())).willReturn(responses);

        // when
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/posts/members/{member-id}", member.getMemberId())
                                .header("Authorization", "Bearer AccessToken")
                                .param("page", "1")
                                .param("size", "10")
                                .accept(MediaType.APPLICATION_JSON)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].postingId").value(posting1.getPostingId()))
                .andExpect(jsonPath("$.data[0].memberId").value(posting1.getMember().getMemberId()))
                .andExpect(jsonPath("$.data[0].userName").value(posting1.getMember().getUserName()))
                .andExpect(jsonPath("$.data[0].profileImage").value(posting1.getMember().getProfileImage()))
                .andExpect(jsonPath("$.data[0].postingContent").value(posting1.getPostingContent()))
                .andExpect(jsonPath("$.data[0].createdAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[0].modifiedAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[1].postingId").value(posting2.getPostingId()))
                .andExpect(jsonPath("$.data[1].memberId").value(posting2.getMember().getMemberId()))
                .andExpect(jsonPath("$.data[1].userName").value(posting2.getMember().getUserName()))
                .andExpect(jsonPath("$.data[1].profileImage").value(posting2.getMember().getProfileImage()))
                .andExpect(jsonPath("$.data[1].postingContent").value(posting2.getPostingContent()))
                .andExpect(jsonPath("$.data[1].createdAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[1].modifiedAt").value("2023-01-01T23:59:59"))
                .andDo(document(
                        "get-postings-by-member",
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
                                fieldWithPath("data[].postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("data[].userName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("data[].profileImage").type(JsonFieldType.STRING).description("회원 이미지"),
                                fieldWithPath("data[].postingContent").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data[].postingMedias[].mediaId").type(JsonFieldType.NUMBER).description("첨부파일 식별자"),
                                fieldWithPath("data[].postingMedias[].mediaUrl").type(JsonFieldType.STRING).description("첨부파일 주소"),
                                fieldWithPath("data[].postingMedias[].thumbnailUrl").type(JsonFieldType.STRING).description("동영상 썸네일 이미지 주소"),
                                fieldWithPath("data[].postingMedias[].format").type(JsonFieldType.STRING).description("첨부파일 형식"),
                                fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("작성일"),
                                fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("최종 수정일"),
                                fieldWithPath("data[].tags[]").type(JsonFieldType.ARRAY).description("태그 이름"),
                                fieldWithPath("data[].likeCount").type(JsonFieldType.NUMBER).description("좋아요 합계"),
                                fieldWithPath("data[].postingLikes").type(JsonFieldType.ARRAY).description("좋아요 누른 회원 리스트"),
                                fieldWithPath("data[].commentCount").type(JsonFieldType.NUMBER).description("댓글 합계"),
                                fieldWithPath("data[].comments").type(JsonFieldType.ARRAY).description("댓글 작성한 회원 리스르"),
                                fieldWithPath("data[].scrapMemberList").type(JsonFieldType.ARRAY).description("해당 게시글을 스크랩한 회원 리스트"),
                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이징 정보"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 사이즈"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                        )
                ));
    }

    @Test
    public void getPostingSortByLikesTest() throws Exception {
        // given
        Member member = new Member();
        member.setMemberId(1L);
        member.setUserName("gimhae_person");
        member.setProfileImage("image");

        Media media1 = new Media(1L, "imageUrl", "", "image", new Posting());
        Media media2 = new Media(2L,"imageUrl", "thumbnail", "video" , new Posting());
        List<Media> mediaList1 = new ArrayList<>();
        mediaList1.add(media1);
        mediaList1.add(media2);

        Media media3 = new Media(1L, "imageUrl", "", "image", new Posting());
        Media media4 = new Media(2L,"imageUrl", "thumbnail", "video" , new Posting());
        List<Media> mediaList2 = new ArrayList<>();
        mediaList2.add(media3);
        mediaList2.add(media4);

        MediaResponseDto response1 = new MediaResponseDto(1L, "imageUrl", "", "image");
        MediaResponseDto response2 = new MediaResponseDto(2L,"imageUrl", "thumbnail", "video");

        List<MediaResponseDto> responseList1 = new ArrayList<>();
        responseList1.add(response1);
        responseList1.add(response2);

        MediaResponseDto response3 = new MediaResponseDto(3L,"imageUrl", "", "image");
        MediaResponseDto response4 = new MediaResponseDto(4L,"imageUrl", "thumbnail", "video");

        List<MediaResponseDto> responseList2 = new ArrayList<>();
        responseList2.add(response3);
        responseList2.add(response4);

        Posting posting1 = new Posting(
                1L,
                "게시글 test1",
                mediaList1,
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Posting posting2 = new Posting(
                2L,
                "게시글 test2",
                mediaList2,
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Page<Posting> pagePostings =
                new PageImpl<>(List.of(posting1, posting2));

        List<PostingResponseDto> responses = List.of(
                new PostingResponseDto(
                        1L,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 test1",
                        responseList1,
                        LocalDateTime.of(2023,01,01,23,59,59),
                        LocalDateTime.of(2023,01,01,23,59,59),
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>()),
                new PostingResponseDto(
                        2L,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 test2",
                        responseList2,
                        LocalDateTime.of(2023,01,01,23,59,59),
                        LocalDateTime.of(2023,01,01,23,59,59),
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>())
        );

        given(postingService.sortPostingsByLikes(Mockito.anyInt(), Mockito.anyInt())).willReturn(pagePostings);
        given(mapper.postingsToPostingsResponseDto(Mockito.anyList())).willReturn(responses);

        // when
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/posts/popular")
                                .header("Authorization", "Bearer AccessToken")
                                .param("page", "1")
                                .param("size", "10")
                                .accept(MediaType.APPLICATION_JSON)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].postingId").value(posting1.getPostingId()))
                .andExpect(jsonPath("$.data[0].memberId").value(posting1.getMember().getMemberId()))
                .andExpect(jsonPath("$.data[0].userName").value(posting1.getMember().getUserName()))
                .andExpect(jsonPath("$.data[0].profileImage").value(posting1.getMember().getProfileImage()))
                .andExpect(jsonPath("$.data[0].postingContent").value(posting1.getPostingContent()))
                .andExpect(jsonPath("$.data[0].createdAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[0].modifiedAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[1].postingId").value(posting2.getPostingId()))
                .andExpect(jsonPath("$.data[1].memberId").value(posting2.getMember().getMemberId()))
                .andExpect(jsonPath("$.data[1].userName").value(posting2.getMember().getUserName()))
                .andExpect(jsonPath("$.data[1].profileImage").value(posting2.getMember().getProfileImage()))
                .andExpect(jsonPath("$.data[1].postingContent").value(posting2.getPostingContent()))
                .andExpect(jsonPath("$.data[1].createdAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[1].modifiedAt").value("2023-01-01T23:59:59"))
                .andDo(document(
                        "get-postings-sorting-by-likes",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        requestParameters(
                                parameterWithName("page").description("조회 할 페이지"),
                                parameterWithName("size").description("조회 할 데이터 갯수")
                        ),
                        responseFields(
                                fieldWithPath("data[].postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("data[].userName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("data[].profileImage").type(JsonFieldType.STRING).description("회원 이미지"),
                                fieldWithPath("data[].postingContent").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data[].postingMedias[].mediaId").type(JsonFieldType.NUMBER).description("첨부파일 식별자"),
                                fieldWithPath("data[].postingMedias[].mediaUrl").type(JsonFieldType.STRING).description("첨부파일 주소"),
                                fieldWithPath("data[].postingMedias[].thumbnailUrl").type(JsonFieldType.STRING).description("동영상 썸네일 이미지 주소"),
                                fieldWithPath("data[].postingMedias[].format").type(JsonFieldType.STRING).description("첨부파일 형식"),
                                fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("작성일"),
                                fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("최종 수정일"),
                                fieldWithPath("data[].tags[]").type(JsonFieldType.ARRAY).description("태그 이름"),
                                fieldWithPath("data[].likeCount").type(JsonFieldType.NUMBER).description("좋아요 합계"),
                                fieldWithPath("data[].postingLikes").type(JsonFieldType.ARRAY).description("좋아요 누른 회원 리스트"),
                                fieldWithPath("data[].commentCount").type(JsonFieldType.NUMBER).description("댓글 합계"),
                                fieldWithPath("data[].comments").type(JsonFieldType.ARRAY).description("댓글 작성한 회원 리스르"),
                                fieldWithPath("data[].scrapMemberList").type(JsonFieldType.ARRAY).description("해당 게시글을 스크랩한 회원 리스트"),
                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이징 정보"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 사이즈"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                        )
                ));
    }

    @Test
    public void getFollowPostingsSortByLikesTest() throws Exception {
        // given
        Member member = new Member();
        member.setMemberId(1L);
        member.setUserName("gimhae_person");
        member.setProfileImage("image");

        Media media1 = new Media(1L, "imageUrl", "", "image", new Posting());
        Media media2 = new Media(2L,"imageUrl", "thumbnail", "video" , new Posting());
        List<Media> mediaList1 = new ArrayList<>();
        mediaList1.add(media1);
        mediaList1.add(media2);

        Media media3 = new Media(1L, "imageUrl", "", "image", new Posting());
        Media media4 = new Media(2L,"imageUrl", "thumbnail", "video" , new Posting());
        List<Media> mediaList2 = new ArrayList<>();
        mediaList2.add(media3);
        mediaList2.add(media4);

        MediaResponseDto response1 = new MediaResponseDto(1L, "imageUrl", "", "image");
        MediaResponseDto response2 = new MediaResponseDto(2L,"imageUrl", "thumbnail", "video");

        List<MediaResponseDto> responseList1 = new ArrayList<>();
        responseList1.add(response1);
        responseList1.add(response2);

        MediaResponseDto response3 = new MediaResponseDto(3L,"imageUrl", "", "image");
        MediaResponseDto response4 = new MediaResponseDto(4L,"imageUrl", "thumbnail", "video");

        List<MediaResponseDto> responseList2 = new ArrayList<>();
        responseList2.add(response3);
        responseList2.add(response4);

        Posting posting1 = new Posting(
                1L,
                "게시글 test1",
                mediaList1,
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Posting posting2 = new Posting(
                2L,
                "게시글 test2",
                mediaList2,
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Page<Posting> pagePostings =
                new PageImpl<>(List.of(posting1, posting2));

        List<PostingResponseDto> responses = List.of(
                new PostingResponseDto(
                        1L,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 test1",
                        responseList1,
                        LocalDateTime.of(2023,01,01,23,59,59),
                        LocalDateTime.of(2023,01,01,23,59,59),
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>()),
                new PostingResponseDto(
                        2L,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 test2",
                        responseList2,
                        LocalDateTime.of(2023,01,01,23,59,59),
                        LocalDateTime.of(2023,01,01,23,59,59),
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>())
        );

        given(postingService.sortFollowPostingsByLikes(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString())).willReturn(pagePostings);
        given(mapper.postingsToPostingsResponseDto(Mockito.anyList())).willReturn(responses);

        // when
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/posts/follow/popular")
                                .header("Authorization", "Bearer AccessToken")
                                .param("page", "1")
                                .param("size", "10")
                                .accept(MediaType.APPLICATION_JSON)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].postingId").value(posting1.getPostingId()))
                .andExpect(jsonPath("$.data[0].memberId").value(posting1.getMember().getMemberId()))
                .andExpect(jsonPath("$.data[0].userName").value(posting1.getMember().getUserName()))
                .andExpect(jsonPath("$.data[0].profileImage").value(posting1.getMember().getProfileImage()))
                .andExpect(jsonPath("$.data[0].postingContent").value(posting1.getPostingContent()))
                .andExpect(jsonPath("$.data[0].createdAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[0].modifiedAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[1].postingId").value(posting2.getPostingId()))
                .andExpect(jsonPath("$.data[1].memberId").value(posting2.getMember().getMemberId()))
                .andExpect(jsonPath("$.data[1].userName").value(posting2.getMember().getUserName()))
                .andExpect(jsonPath("$.data[1].profileImage").value(posting2.getMember().getProfileImage()))
                .andExpect(jsonPath("$.data[1].postingContent").value(posting2.getPostingContent()))
                .andExpect(jsonPath("$.data[1].createdAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[1].modifiedAt").value("2023-01-01T23:59:59"))
                .andDo(document(
                        "get-following-postings-sorting-by-likes",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        requestParameters(
                                parameterWithName("page").description("조회 할 페이지"),
                                parameterWithName("size").description("조회 할 데이터 갯수")
                        ),
                        responseFields(
                                fieldWithPath("data[].postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("data[].userName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("data[].profileImage").type(JsonFieldType.STRING).description("회원 이미지"),
                                fieldWithPath("data[].postingContent").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data[].postingMedias[].mediaId").type(JsonFieldType.NUMBER).description("첨부파일 식별자"),
                                fieldWithPath("data[].postingMedias[].mediaUrl").type(JsonFieldType.STRING).description("첨부파일 주소"),
                                fieldWithPath("data[].postingMedias[].thumbnailUrl").type(JsonFieldType.STRING).description("동영상 썸네일 이미지 주소"),
                                fieldWithPath("data[].postingMedias[].format").type(JsonFieldType.STRING).description("첨부파일 형식"),
                                fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("작성일"),
                                fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("최종 수정일"),
                                fieldWithPath("data[].tags[]").type(JsonFieldType.ARRAY).description("태그 이름"),
                                fieldWithPath("data[].likeCount").type(JsonFieldType.NUMBER).description("좋아요 합계"),
                                fieldWithPath("data[].postingLikes").type(JsonFieldType.ARRAY).description("좋아요 누른 회원 리스트"),
                                fieldWithPath("data[].commentCount").type(JsonFieldType.NUMBER).description("댓글 합계"),
                                fieldWithPath("data[].comments").type(JsonFieldType.ARRAY).description("댓글 작성한 회원 리스르"),
                                fieldWithPath("data[].scrapMemberList").type(JsonFieldType.ARRAY).description("해당 게시글을 스크랩한 회원 리스트"),
                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이징 정보"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 사이즈"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                        )
                ));
    }

    @Test
    public void getPostingsByStrTest() throws Exception {
        // given
        Member member = new Member();
        member.setMemberId(1L);
        member.setUserName("gimhae_person");
        member.setProfileImage("image");

        Media media1 = new Media(1L, "imageUrl", "", "image", new Posting());
        Media media2 = new Media(2L,"imageUrl", "thumbnail", "video" , new Posting());
        List<Media> mediaList1 = new ArrayList<>();
        mediaList1.add(media1);
        mediaList1.add(media2);

        Media media3 = new Media(1L, "imageUrl", "", "image", new Posting());
        Media media4 = new Media(2L,"imageUrl", "thumbnail", "video" , new Posting());
        List<Media> mediaList2 = new ArrayList<>();
        mediaList2.add(media3);
        mediaList2.add(media4);

        MediaResponseDto response1 = new MediaResponseDto(1L, "imageUrl", "", "image");
        MediaResponseDto response2 = new MediaResponseDto(2L,"imageUrl", "thumbnail", "video");

        List<MediaResponseDto> responseList1 = new ArrayList<>();
        responseList1.add(response1);
        responseList1.add(response2);

        MediaResponseDto response3 = new MediaResponseDto(3L,"imageUrl", "", "image");
        MediaResponseDto response4 = new MediaResponseDto(4L,"imageUrl", "thumbnail", "video");

        List<MediaResponseDto> responseList2 = new ArrayList<>();
        responseList2.add(response3);
        responseList2.add(response4);

        Posting posting1 = new Posting(
                1L,
                "게시글 test1",
                mediaList1,
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Posting posting2 = new Posting(
                2L,
                "게시글 test2",
                mediaList2,
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Page<Posting> pagePostings =
                new PageImpl<>(List.of(posting1, posting2));

        List<PostingResponseDto> responses = List.of(
                new PostingResponseDto(
                        1L,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 test1",
                        responseList1,
                        LocalDateTime.of(2023,01,01,23,59,59),
                        LocalDateTime.of(2023,01,01,23,59,59),
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>()),
                new PostingResponseDto(
                        2L,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 test2",
                        responseList2,
                        LocalDateTime.of(2023,01,01,23,59,59),
                        LocalDateTime.of(2023,01,01,23,59,59),
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>())
        );

        given(postingService.findPostingsByStrContent(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString())).willReturn(pagePostings);
        given(mapper.postingsToPostingsResponseDto(Mockito.anyList())).willReturn(responses);

        // when
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/posts/search")
                                .header("Authorization", "Bearer AccessToken")
                                .param("str", "게시글")
                                .param("page", "1")
                                .param("size", "10")
                                .accept(MediaType.APPLICATION_JSON)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].postingId").value(posting1.getPostingId()))
                .andExpect(jsonPath("$.data[0].memberId").value(posting1.getMember().getMemberId()))
                .andExpect(jsonPath("$.data[0].userName").value(posting1.getMember().getUserName()))
                .andExpect(jsonPath("$.data[0].profileImage").value(posting1.getMember().getProfileImage()))
                .andExpect(jsonPath("$.data[0].postingContent").value(posting1.getPostingContent()))
                .andExpect(jsonPath("$.data[0].createdAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[0].modifiedAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[1].postingId").value(posting2.getPostingId()))
                .andExpect(jsonPath("$.data[1].memberId").value(posting2.getMember().getMemberId()))
                .andExpect(jsonPath("$.data[1].userName").value(posting2.getMember().getUserName()))
                .andExpect(jsonPath("$.data[1].profileImage").value(posting2.getMember().getProfileImage()))
                .andExpect(jsonPath("$.data[1].postingContent").value(posting2.getPostingContent()))
                .andExpect(jsonPath("$.data[1].createdAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[1].modifiedAt").value("2023-01-01T23:59:59"))
                .andDo(document(
                        "search-postings-by-str",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        requestParameters(
                                parameterWithName("str").description("검색 할 단어"),
                                parameterWithName("page").description("조회 할 페이지"),
                                parameterWithName("size").description("조회 할 데이터 갯수")
                        ),
                        responseFields(
                                fieldWithPath("data[].postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("data[].userName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("data[].profileImage").type(JsonFieldType.STRING).description("회원 이미지"),
                                fieldWithPath("data[].postingContent").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data[].postingMedias[].mediaId").type(JsonFieldType.NUMBER).description("첨부파일 식별자"),
                                fieldWithPath("data[].postingMedias[].mediaUrl").type(JsonFieldType.STRING).description("첨부파일 주소"),
                                fieldWithPath("data[].postingMedias[].thumbnailUrl").type(JsonFieldType.STRING).description("동영상 썸네일 이미지 주소"),
                                fieldWithPath("data[].postingMedias[].format").type(JsonFieldType.STRING).description("첨부파일 형식"),
                                fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("작성일"),
                                fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("최종 수정일"),
                                fieldWithPath("data[].tags[]").type(JsonFieldType.ARRAY).description("태그 이름"),
                                fieldWithPath("data[].likeCount").type(JsonFieldType.NUMBER).description("좋아요 합계"),
                                fieldWithPath("data[].postingLikes").type(JsonFieldType.ARRAY).description("좋아요 누른 회원 리스트"),
                                fieldWithPath("data[].commentCount").type(JsonFieldType.NUMBER).description("댓글 합계"),
                                fieldWithPath("data[].comments").type(JsonFieldType.ARRAY).description("댓글 작성한 회원 리스르"),
                                fieldWithPath("data[].scrapMemberList").type(JsonFieldType.ARRAY).description("해당 게시글을 스크랩한 회원 리스트"),
                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이징 정보"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 사이즈"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                        )
                ));
    }

    @Test
    public void getPostingTagsByStrTest() throws Exception {
        // given
        Member member = new Member();
        member.setMemberId(1L);
        member.setUserName("gimhae_person");
        member.setProfileImage("image");

        Media media1 = new Media(1L, "imageUrl", "", "image", new Posting());
        Media media2 = new Media(2L,"imageUrl", "thumbnail", "video" , new Posting());
        List<Media> mediaList1 = new ArrayList<>();
        mediaList1.add(media1);
        mediaList1.add(media2);

        Media media3 = new Media(1L, "imageUrl", "", "image", new Posting());
        Media media4 = new Media(2L,"imageUrl", "thumbnail", "video" , new Posting());
        List<Media> mediaList2 = new ArrayList<>();
        mediaList2.add(media3);
        mediaList2.add(media4);

        MediaResponseDto response1 = new MediaResponseDto(1L, "imageUrl", "", "image");
        MediaResponseDto response2 = new MediaResponseDto(2L,"imageUrl", "thumbnail", "video");

        List<MediaResponseDto> responseList1 = new ArrayList<>();
        responseList1.add(response1);
        responseList1.add(response2);

        MediaResponseDto response3 = new MediaResponseDto(3L,"imageUrl", "", "image");
        MediaResponseDto response4 = new MediaResponseDto(4L,"imageUrl", "thumbnail", "video");

        List<MediaResponseDto> responseList2 = new ArrayList<>();
        responseList2.add(response3);
        responseList2.add(response4);

        Tag tag1 = new Tag();
        tag1.setTagName("식물1");

        Tag tag2 = new Tag();
        tag1.setTagName("식물2");

        PostingTags tag = new PostingTags();
        tag.setTag(tag1);
        tag.setTag(tag2);

        List<PostingTags> tags = new ArrayList<>();
        tags.add(tag);

        PostingTagsResponseDto tagResponseDto1 = new PostingTagsResponseDto("식물1");
        PostingTagsResponseDto tagResponseDto2 = new PostingTagsResponseDto("식물2");

        List<PostingTagsResponseDto> tagsResponseDto = new ArrayList<>();
        tagsResponseDto.add(tagResponseDto1);
        tagsResponseDto.add(tagResponseDto2);

        Posting posting1 = new Posting(
                1L,
                "게시글 test1",
                mediaList1,
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member,
                new ArrayList<>(),
                tags,
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Posting posting2 = new Posting(
                2L,
                "게시글 test2",
                mediaList2,
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member,
                new ArrayList<>(),
                tags,
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Page<Posting> pagePostings =
                new PageImpl<>(List.of(posting1, posting2));

        List<PostingResponseDto> responses = List.of(
                new PostingResponseDto(
                        1L,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 test1",
                        responseList1,
                        LocalDateTime.of(2023,01,01,23,59,59),
                        LocalDateTime.of(2023,01,01,23,59,59),
                        tagsResponseDto,
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>()),
                new PostingResponseDto(
                        2L,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 test2",
                        responseList2,
                        LocalDateTime.of(2023,01,01,23,59,59),
                        LocalDateTime.of(2023,01,01,23,59,59),
                        tagsResponseDto,
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>())
        );

        given(postingService.findPostingsByStrTag(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString())).willReturn(pagePostings);
        given(mapper.postingsToPostingsResponseDto(Mockito.anyList())).willReturn(responses);

        // when
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/posts/tag/search")
                                .header("Authorization", "Bearer AccessToken")
                                .param("str", "식물")
                                .param("page", "1")
                                .param("size", "10")
                                .accept(MediaType.APPLICATION_JSON)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].postingId").value(posting1.getPostingId()))
                .andExpect(jsonPath("$.data[0].memberId").value(posting1.getMember().getMemberId()))
                .andExpect(jsonPath("$.data[0].userName").value(posting1.getMember().getUserName()))
                .andExpect(jsonPath("$.data[0].profileImage").value(posting1.getMember().getProfileImage()))
                .andExpect(jsonPath("$.data[0].postingContent").value(posting1.getPostingContent()))
                .andExpect(jsonPath("$.data[0].createdAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[0].modifiedAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[1].postingId").value(posting2.getPostingId()))
                .andExpect(jsonPath("$.data[1].memberId").value(posting2.getMember().getMemberId()))
                .andExpect(jsonPath("$.data[1].userName").value(posting2.getMember().getUserName()))
                .andExpect(jsonPath("$.data[1].profileImage").value(posting2.getMember().getProfileImage()))
                .andExpect(jsonPath("$.data[1].postingContent").value(posting2.getPostingContent()))
                .andExpect(jsonPath("$.data[1].createdAt").value("2023-01-01T23:59:59"))
                .andExpect(jsonPath("$.data[1].modifiedAt").value("2023-01-01T23:59:59"))
                .andDo(document(
                        "search-postings-by-tagName",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        requestParameters(
                                parameterWithName("str").description("검색 할 단어"),
                                parameterWithName("page").description("조회 할 페이지"),
                                parameterWithName("size").description("조회 할 데이터 갯수")
                        ),
                        responseFields(
                                fieldWithPath("data[].postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("data[].userName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("data[].profileImage").type(JsonFieldType.STRING).description("회원 이미지"),
                                fieldWithPath("data[].postingContent").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data[].postingMedias[].mediaId").type(JsonFieldType.NUMBER).description("첨부파일 식별자"),
                                fieldWithPath("data[].postingMedias[].mediaUrl").type(JsonFieldType.STRING).description("첨부파일 주소"),
                                fieldWithPath("data[].postingMedias[].thumbnailUrl").type(JsonFieldType.STRING).description("동영상 썸네일 이미지 주소"),
                                fieldWithPath("data[].postingMedias[].format").type(JsonFieldType.STRING).description("첨부파일 형식"),
                                fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("작성일"),
                                fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("최종 수정일"),
                                fieldWithPath("data[].tags[].tagName").type(JsonFieldType.STRING).description("태그 이름"),
                                fieldWithPath("data[].likeCount").type(JsonFieldType.NUMBER).description("좋아요 합계"),
                                fieldWithPath("data[].postingLikes").type(JsonFieldType.ARRAY).description("좋아요 누른 회원 리스트"),
                                fieldWithPath("data[].commentCount").type(JsonFieldType.NUMBER).description("댓글 합계"),
                                fieldWithPath("data[].comments").type(JsonFieldType.ARRAY).description("댓글 작성한 회원 리스르"),
                                fieldWithPath("data[].scrapMemberList").type(JsonFieldType.ARRAY).description("해당 게시글을 스크랩한 회원 리스트"),
                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이징 정보"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 사이즈"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                        )
                ));
    }

    @Test
    public void deletePostingTest() throws Exception {
        // given
        long postingId = 1L;
        doNothing().when(postingService).deletePosting(Mockito.anyLong(), Mockito.anyString());
        
        // when
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/posts/{posting-id}", postingId)
                                .header("Authorization", "Bearer AccessToken")
                );

        // then
        actions.andExpect(status().isNoContent())
                .andDo(
                        document(
                                "delete-posting",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(parameterWithName("posting-id").description("게시글 식별자")
                                ),
                                requestHeaders(
                                        headerWithName("Authorization").description("Bearer (accessToken)")
                                )
                        ));
    }
    
    @Test
    public void postMediaTest() throws Exception {
        // given
        long postingId = 1L;
        long memberId = 1L;

        MediaResponseDto response1 = new MediaResponseDto(1L, "imageUrl", "", "image");
        MediaResponseDto response2 = new MediaResponseDto(2L,"imageUrl", "thumbnail", "video");


        List<MediaResponseDto> responseList = new ArrayList<>();
        responseList.add(response1);
        responseList.add(response2);


        MockMultipartFile file1 = new MockMultipartFile("file1", "Image.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());

        PostingResponseDto response =
                new PostingResponseDto(
                        postingId,
                        memberId,
                        "gimhae_person",
                        "image",
                        "게시글 test",
                        responseList,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>()
                );

        given(postingService.addMedia(Mockito.anyLong(), Mockito.anyList(), Mockito.anyString())).willReturn(new Posting());
        given(mapper.postingToPostingResponseDto(Mockito.any(Posting.class))).willReturn(response);
        // when
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.multipart("/posts/{posting-id}/medias", postingId)
                                .file(file1)
                                .header("Authorization", "Bearer AccessToken")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                );
        // then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "post-posting-media",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        pathParameters(
                                parameterWithName("posting-id").description("게시글 식별자")
                        ),
                        requestParts(
                                RequestDocumentation.partWithName("file1").description("첨부파일1"),
                                RequestDocumentation.partWithName("file2").optional().description("첨부파일2"),
                                RequestDocumentation.partWithName("file3").optional().description("첨부파일3")
                        ),
                        responseFields(
                                fieldWithPath("data.postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("data.userName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("회원 이미지"),
                                fieldWithPath("data.postingContent").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.postingMedias[].mediaId").type(JsonFieldType.NUMBER).description("첨부파일 식별자"),
                                fieldWithPath("data.postingMedias[].mediaUrl").type(JsonFieldType.STRING).description("첨부파일 주소"),
                                fieldWithPath("data.postingMedias[].thumbnailUrl").type(JsonFieldType.STRING).description("동영상 썸네일 이미지 주소"),
                                fieldWithPath("data.postingMedias[].format").type(JsonFieldType.STRING).description("첨부파일 형식"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("작성일"),
                                fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("최종 수정일"),
                                fieldWithPath("data.tags[]").type(JsonFieldType.ARRAY).description("태그 이름"),
                                fieldWithPath("data.likeCount").type(JsonFieldType.NUMBER).description("좋아요 합계"),
                                fieldWithPath("data.postingLikes").type(JsonFieldType.ARRAY).description("좋아요 누른 회원 리스트"),
                                fieldWithPath("data.commentCount").type(JsonFieldType.NUMBER).description("댓글 합계"),
                                fieldWithPath("data.comments").type(JsonFieldType.ARRAY).description("댓글 작성한 회원 리스르"),
                                fieldWithPath("data.scrapMemberList").type(JsonFieldType.ARRAY).description("해당 게시글을 스크랩한 회원 리스트")
                        )
                ));
    }

    @Test
    public void deleteMedia() throws Exception {
        // given
        long mediaId = 1L;

        doNothing().when(postingService).deleteMedia(Mockito.anyLong(), Mockito.anyString());

        // when
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/posts/medias/{media-id}", mediaId)
                                .header("Authorization", "Bearer AccessToken")
                );

        // then
        actions.andExpect(status().isNoContent())
                .andDo(
                        document(
                                "delete-media",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(parameterWithName("media-id").description("첨부파일 식별자")
                                ),
                                requestHeaders(
                                        headerWithName("Authorization").description("Bearer (accessToken)")
                                )
                        ));
    }
}
