package com.main19.server.posting;

import com.google.gson.Gson;
import com.main19.server.member.entity.Member;
import com.main19.server.posting.controller.PostingController;
import com.main19.server.posting.dto.MediaPostDto;
import com.main19.server.posting.dto.PostingPatchDto;
import com.main19.server.posting.dto.PostingPostDto;
import com.main19.server.posting.dto.PostingResponseDto;
import com.main19.server.posting.entity.Posting;
import com.main19.server.posting.mapper.PostingMapper;
import com.main19.server.posting.service.PostingService;
import com.main19.server.posting.tags.dto.PostingTagsResponseDto;
import com.main19.server.posting.tags.entity.PostingTags;
import com.main19.server.posting.tags.entity.Tag;
import com.main19.server.posting.tags.service.PostingTagsService;
import com.main19.server.posting.tags.service.TagService;
import com.main19.server.s3service.S3StorageService;
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
@AutoConfigureRestDocs
public class PostingControllerRestDocs {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private PostingService postingService;
    @MockBean
    private S3StorageService storageService;
    @MockBean
    private TagService tagService;
    @MockBean
    private PostingTagsService postingTagsService;
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
        MockMultipartFile requestBody = new MockMultipartFile("requestBody", "", "application/json", content.getBytes(StandardCharsets.UTF_8));

        PostingResponseDto response =
                new PostingResponseDto(
                        postingId,
                        memberId,
                        "gimhae_person",
                        "image",
                        "게시글 test",
                        new ArrayList<>(),
                        createdAt,
                        modifiedAt,
                        tags,
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>()
                );

        given(storageService.uploadMedia(Mockito.anyList(), Mockito.anyLong(), Mockito.anyString())).willReturn(new ArrayList<>());
        given(mapper.postingPostDtoToPosting(Mockito.any(PostingPostDto.class))).willReturn(new Posting());
        given(postingService.createPosting(Mockito.any(Posting.class), Mockito.anyLong(), Mockito.anyList())).willReturn(new Posting());
        given(mapper.tagPostDtoToTag(Mockito.anyString())).willReturn(new Tag());
        given(tagService.createTag(Mockito.any(Tag.class))).willReturn(new Tag());
        given(mapper.postingPostDtoToPostingTag(Mockito.any(PostingPostDto.class))).willReturn(new PostingTags());
        given(postingTagsService.createPostingTags(Mockito.any(PostingTags.class), Mockito.any(Posting.class), Mockito.anyString())).willReturn(new PostingTags());
        given(mapper.postingToPostingResponseDto(Mockito.any(Posting.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.multipart("/posts")
                        .file(file1)
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
                                fieldWithPath("data.postingMedias").type(JsonFieldType.ARRAY).description("첨부파일 리스트"),
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

        PostingPatchDto patch = new PostingPatchDto(postingId, postingContent, tagName);

        PostingTagsResponseDto tag1 = new PostingTagsResponseDto("스투키");
        PostingTagsResponseDto tag2 = new PostingTagsResponseDto("몬스테라");

        List<PostingTagsResponseDto> tags = new ArrayList<>();
        tags.add(tag1);
        tags.add(tag2);

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
                        new ArrayList<>(),
                        createdAt,
                        modifiedAt,
                        tags,
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>()
                );

        given(mapper.postingPatchDtoToPosting(Mockito.any(PostingPatchDto.class))).willReturn(new Posting());
        given(postingService.updatePosting(Mockito.any(Posting.class), Mockito.anyString())).willReturn(new Posting());
        given(mapper.tagPostDtoToTag(Mockito.anyString())).willReturn(new Tag());
        given(tagService.createTag(Mockito.any(Tag.class))).willReturn(new Tag());
        given(mapper.postingPatchDtoToPostingTag(Mockito.any(PostingPatchDto.class))).willReturn(new PostingTags());
        given(postingTagsService.updatePostingTags(Mockito.any(PostingTags.class), Mockito.anyLong(), Mockito.anyString())).willReturn(new PostingTags());
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
                .andExpect(jsonPath("$.data.postingId").value(patch.getPostingId()))
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
                                List.of(fieldWithPath("postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
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
                                fieldWithPath("data.postingMedias").type(JsonFieldType.ARRAY).description("첨부파일 리스트"),
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
        long postingId = 1L;
        PostingResponseDto response =
                new PostingResponseDto(
                        postingId,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 test",
                        new ArrayList<>(),
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
                                fieldWithPath("data.postingMedias").type(JsonFieldType.ARRAY).description("첨부파일 리스트"),
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

        Posting posting1 = new Posting(
                1L,
                "게시글 test1",
                new ArrayList<>(),
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member1,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>()
        );

        Posting posting2 = new Posting(
                2L,
                "게시글 test2",
                new ArrayList<>(),
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member2,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>()
        );
        List<Posting> content = List.of(posting1, posting2);

        Page<Posting> pagePostings =
                new PageImpl<>(List.of(posting1, posting2));

        List<PostingResponseDto> responses = List.of(
                new PostingResponseDto(
                        1L,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 test1",
                        new ArrayList<>(),
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
                        new ArrayList<>(),
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
                        "get-all-posting",
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
                                fieldWithPath("data[].postingMedias").type(JsonFieldType.ARRAY).description("첨부파일 리스트"),
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

        Posting posting1 = new Posting(
                1L,
                "게시글 test1",
                new ArrayList<>(),
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>()
        );

        Posting posting2 = new Posting(
                2L,
                "게시글 test2",
                new ArrayList<>(),
                LocalDateTime.of(2023,01,01,23,59,59),
                LocalDateTime.of(2023,01,01,23,59,59),
                member,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                0L,
                0L,
                new ArrayList<>()
        );

        List<Posting> content = List.of(posting1, posting2);

        Page<Posting> pagePostings =
                new PageImpl<>(List.of(posting1, posting2));

        List<PostingResponseDto> responses = List.of(
                new PostingResponseDto(
                        1L,
                        1L,
                        "gimhae_person",
                        "image",
                        "게시글 test1",
                        new ArrayList<>(),
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
                        new ArrayList<>(),
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
                                fieldWithPath("data[].postingMedias").type(JsonFieldType.ARRAY).description("첨부파일 리스트"),
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
    public void deletePostingTest() throws Exception {
        // given
        long postingId = 1L;
        doNothing().when(storageService).removeAll(Mockito.anyLong(), Mockito.anyString());
        doNothing().when(postingService).deletePosting(Mockito.anyLong());
        
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
        MediaPostDto post = new MediaPostDto(memberId);

        String content = gson.toJson(post);

        MockMultipartFile file1 = new MockMultipartFile("file1", "Image.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());
        MockMultipartFile requestBody = new MockMultipartFile("requestBody", "", "application/json", content.getBytes(StandardCharsets.UTF_8));

        PostingResponseDto response =
                new PostingResponseDto(
                        postingId,
                        memberId,
                        "gimhae_person",
                        "image",
                        "게시글 test",
                        new ArrayList<>(),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        0L,
                        new ArrayList<>(),
                        new ArrayList<>()
                );

        given(storageService.uploadMedia(Mockito.anyList(), Mockito.anyLong(), Mockito.anyString())).willReturn(new ArrayList<>());
        given(postingService.addMedia(Mockito.anyLong(), Mockito.anyList())).willReturn(new Posting());
        given(mapper.postingToPostingResponseDto(Mockito.any(Posting.class))).willReturn(response);
        // when
        ResultActions actions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.multipart("/posts/{posting-id}/medias", postingId)
                                .file(file1)
                                .file(requestBody)
                                .header("Authorization", "Bearer AccessToken")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .content(content)
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
                                RequestDocumentation.partWithName("requestBody").description("회원 식별자"),
                                RequestDocumentation.partWithName("file1").description("첨부파일1"),
                                RequestDocumentation.partWithName("file2").optional().description("첨부파일2")
                        ),
                        requestPartFields(
                                "requestBody",
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자")
                        ),
                        responseFields(
                                fieldWithPath("data.postingId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("data.userName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("회원 이미지"),
                                fieldWithPath("data.postingContent").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.postingMedias").type(JsonFieldType.ARRAY).description("첨부파일 리스트"),
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

        doNothing().when(storageService).remove(Mockito.anyLong(), Mockito.anyString());
        doNothing().when(postingService).deleteMedia(Mockito.anyLong());

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
