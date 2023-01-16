package com.main19.server.posting;

import com.amazonaws.Request;
import com.google.gson.Gson;
import com.main19.server.auth.jwt.JwtTokenizer;
import com.main19.server.comment.dto.CommentDto;
import com.main19.server.comment.entity.Comment;
import com.main19.server.member.controller.MemberController;
import com.main19.server.member.mapper.MemberMapper;
import com.main19.server.member.service.MemberService;
import com.main19.server.posting.controller.PostingController;
import com.main19.server.posting.dto.PostingPatchDto;
import com.main19.server.posting.dto.PostingPostDto;
import com.main19.server.posting.dto.PostingResponseDto;
import com.main19.server.posting.entity.Posting;
import com.main19.server.posting.like.repository.PostingLikeRepository;
import com.main19.server.posting.mapper.PostingMapper;
import com.main19.server.posting.service.PostingService;
import com.main19.server.posting.tags.dto.PostingTagsResponseDto;
import com.main19.server.posting.tags.dto.TagPostDto;
import com.main19.server.posting.tags.entity.PostingTags;
import com.main19.server.posting.tags.entity.Tag;
import com.main19.server.posting.tags.service.PostingTagsService;
import com.main19.server.posting.tags.service.TagService;
import com.main19.server.s3service.S3StorageService;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.main19.server.utils.DocumentUtils.getRequestPreProcessor;
import static com.main19.server.utils.DocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @Autowired
    private PostingLikeRepository postingLikeRepository;

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
        MockMultipartFile multipartFiles = new MockMultipartFile("multipartFiles", "profileImage.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());
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

        given(storageService.uploadMedia(Mockito.anyList())).willReturn(new ArrayList<>());
        given(mapper.postingPostDtoToPosting(Mockito.any(PostingPostDto.class))).willReturn(new Posting());
        given(postingService.createPosting(Mockito.any(Posting.class), Mockito.anyLong(), Mockito.anyList(), Mockito.anyString())).willReturn(new Posting());
        given(mapper.tagPostDtoToTag(Mockito.anyString())).willReturn(new Tag());
        given(tagService.createTag(Mockito.any(Tag.class))).willReturn(new Tag());
        given(mapper.postingPostDtoToPostingTag(Mockito.any(PostingPostDto.class))).willReturn(new PostingTags());
        given(postingTagsService.createPostingTags(Mockito.any(PostingTags.class), Mockito.any(Posting.class), Mockito.anyString())).willReturn(new PostingTags());
        given(mapper.postingToPostingResponseDto(Mockito.any(Posting.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.multipart("/posts")
                        .file(multipartFiles)
                        .file(requestBody)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .contentType(MediaType.APPLICATION_JSON)
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
                                RequestDocumentation.partWithName("multipartFiles").description("첨부파일"),
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
                        LocalDateTime.now(),
                        LocalDateTime.now(),
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
    
//    @Test
//    public void getPostingsTest() throws Exception {
//        // given
//        long postingId1 = 1L;
//        PostingResponseDto response1 =
//                new PostingResponseDto(
//                        postingId1,
//                        1L,
//                        "gimhae_person",
//                        "image",
//                        "게시글 test1",
//                        new ArrayList<>(),
//                        LocalDateTime.now(),
//                        LocalDateTime.now(),
//                        new ArrayList<>(),
//                        0L,
//                        new ArrayList<>(),
//                        0L,
//                        new ArrayList<>(),
//                        new ArrayList<>()
//                );
//
//        long postingId2 = 2L;
//        PostingResponseDto response =
//                new PostingResponseDto(
//                        postingId2,
//                        1L,
//                        "taebong98",
//                        "image",
//                        "게시글 test2",
//                        new ArrayList<>(),
//                        LocalDateTime.now(),
//                        LocalDateTime.now(),
//                        new ArrayList<>(),
//                        0L,
//                        new ArrayList<>(),
//                        0L,
//                        new ArrayList<>(),
//                        new ArrayList<>()
//                );
//
//        // when
//
//        // then
//    }
}
