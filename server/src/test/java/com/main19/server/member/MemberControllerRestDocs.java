package com.main19.server.member;

import com.google.gson.Gson;
import com.main19.server.global.auth.jwt.JwtTokenizer;
import com.main19.server.domain.member.controller.MemberController;
import com.main19.server.domain.member.dto.MemberDto;
import com.main19.server.domain.member.dto.MemberDto.Response;
import com.main19.server.domain.member.entity.Member;
import com.main19.server.domain.member.mapper.MemberMapper;
import com.main19.server.domain.member.service.MemberService;
import com.main19.server.global.storageService.s3.ProfileStorageService;
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

import java.util.ArrayList;
import java.util.List;

import static com.main19.server.utils.DocumentUtils.getRequestPreProcessor;
import static com.main19.server.utils.DocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = MemberController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriHost = "increaf.site")
public class MemberControllerRestDocs {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private MemberService memberService;
    @MockBean
    private MemberMapper mapper;
    @MockBean
    private JwtTokenizer jwtTokenizer;
    @MockBean
    private ProfileStorageService storageService;


    @Test
    public void postMemberTest() throws Exception {
        // given
        MemberDto.Post post = new MemberDto.Post("tae", "taebong98", "??????????????????", "????????????", "q1w2e3r4!!");
        String content = gson.toJson(post);

        MemberDto.Response response =
            new MemberDto.Response(
                1L,
                "tae",
                "taebong98",
                "??????????????????",
                null,
                "????????????",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());

        given(mapper.memberPostToMember(Mockito.any(MemberDto.Post.class))).willReturn(new Member());
        given(memberService.createMember(Mockito.any(Member.class))).willReturn(new Member());
        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
            post("/members/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
        );

        // then
        actions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.userName").value(post.getUserName()))
            .andExpect(jsonPath("$.data.email").value(post.getEmail()))
            .andExpect(jsonPath("$.data.location").value(post.getLocation()))
            .andExpect(jsonPath("$.data.profileText").value(post.getProfileText()))

            .andDo(document(
                "post-member",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestFields(
                    List.of(
                        fieldWithPath("userName").type(JsonFieldType.STRING).description("?????????"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("?????????"),
                        fieldWithPath("location").type(JsonFieldType.STRING).description("??????"),
                        fieldWithPath("profileText").type(JsonFieldType.STRING).description("????????????"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("????????????")
                    )
                ),
                responseFields(
                    List.of(
                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????????"),
                        fieldWithPath("data.userName").type(JsonFieldType.STRING).description("?????????"),
                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("?????????"),
                        fieldWithPath("data.location").type(JsonFieldType.STRING).description("??????"),
                        fieldWithPath("data.profileImage").type(JsonFieldType.NULL).description("???????????????"),
                        fieldWithPath("data.profileText").type(JsonFieldType.STRING).description("????????????"),
                        fieldWithPath("data.scrapPostingList").type(JsonFieldType.ARRAY).description("???????????? ?????????"),
                        fieldWithPath("data.followingList").type(JsonFieldType.ARRAY).description("????????? ??????"),
                        fieldWithPath("data.followerList").type(JsonFieldType.ARRAY).description("????????? ??????")

                    )
                )
            ));
    }

    @Test
    public void MemberProfileImageTest() throws Exception {
        // given

        long memberId = 1L;

        MockMultipartFile profileImage = new MockMultipartFile("profileImage", "profileImage.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());

        MemberDto.Response response =
            new MemberDto.Response(
                1L,
                "taebong98",
                "taebong98",
                "??????????????????",
                "profileImage.jpeg",
                "????????????",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());


        given(storageService.uploadProfileImage(Mockito.any(), Mockito.anyLong())).willReturn("profileImageUrl");
        given(memberService.createProfileImage(Mockito.anyLong(),Mockito.anyString(),Mockito.anyString())).willReturn(new Member());
        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
            RestDocumentationRequestBuilders.multipart("/members/{member-id}/profileimage",memberId)
                .file(profileImage)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization","Bear AccessToken")
        );

        // then
        actions
            .andExpect(status().isCreated())
            .andDo(document(
                    "post-member-image",
                    getRequestPreProcessor(),
                    getResponsePreProcessor(),
                    requestHeaders(
                        headerWithName("Authorization").description("Bearer AccessToken")
                    ),
                    pathParameters(
                        parameterWithName("member-id").description("?????? ?????????")
                    ),
                    RequestDocumentation.requestParts
                        (RequestDocumentation.partWithName("profileImage").description("?????? ?????????")),
                    responseFields(
                        List.of(
                            fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                            fieldWithPath("userName").type(JsonFieldType.STRING).description("?????? ?????????"),
                            fieldWithPath("email").type(JsonFieldType.STRING).description("?????? ?????????"),
                            fieldWithPath("location").type(JsonFieldType.STRING).description("?????? ??????"),
                            fieldWithPath("profileImage").type(JsonFieldType.STRING).description("?????? ????????? ?????????"),
                            fieldWithPath("profileText").type(JsonFieldType.STRING).description("?????? ??????"),
                            fieldWithPath("scrapPostingList").type(JsonFieldType.ARRAY).description("????????? ?????????"),
                            fieldWithPath("followingList").type(JsonFieldType.ARRAY).description("????????? ??????"),
                            fieldWithPath("followerList").type(JsonFieldType.ARRAY).description("????????? ??????")
                        )
                    )

                )
            );
    }

    @Test
    public void deleteProfileImageTest() throws Exception {
        // given
        long memberId = 1L;

        doNothing().when(storageService).removeProfileImage(memberId);
        doNothing().when(memberService).deleteMember(memberId, "token");

        // when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/members/{member-id}/profileimage", memberId)
                        .header("Authorization", "Bearer AccessToken")
        );

        // then
        actions
                .andExpect(status().isNoContent())
                .andDo(document(
                        "delete-member-profileImage",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("member-id").description("?????? ?????????")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        )
                ));
    }

    @Test
    public void patchMemberTest() throws Exception {
        // given
        long memberId = 1L;
        MemberDto.Patch patch = new MemberDto.Patch("?????????", "?????? ?????? ??????", "??????????????????");
        patch.setMemberId(memberId);
        String content = gson.toJson(patch);
        MemberDto.Response response = new MemberDto.Response(memberId, "?????????", "taebong98", "??????????????????", "", "?????? ?????? ??????", new ArrayList<>(), new ArrayList<>(),new ArrayList<>());

        // when
        given(mapper.memberPatchToMember(Mockito.any(MemberDto.Patch.class))).willReturn(new Member());
        given(memberService.updateMember(Mockito.any(Member.class), Mockito.anyString())).willReturn(new Member());
        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(response);

        // then
        ResultActions actions = mockMvc.perform(
            RestDocumentationRequestBuilders.patch("/members/{member-id}", memberId)
                .header("Authorization", "Bear AccessToken")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );

        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.data.memberId").value(patch.getMemberId()))
            .andExpect(jsonPath("$.data.userName").value(patch.getUserName()))
            .andExpect(jsonPath("$.data.profileText").value(patch.getProfileText()))
            .andExpect(jsonPath("$.data.location").value(patch.getLocation()))
            .andDo(document(
                "patch-member",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer AccessToken")
                ),
                pathParameters(
                    parameterWithName("member-id").description("?????? ?????????")
                ),
                requestFields(
                    List.of(
                        fieldWithPath("memberId").description("?????? ?????????"),
                        fieldWithPath("userName").description("?????????"),
                        fieldWithPath("profileText").description("????????????"),
                        fieldWithPath("location").description("??????")
                    )
                ),
                responseFields(
                    List.of(
                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                        fieldWithPath("data.userName").type(JsonFieldType.STRING).description("?????? ?????????"),
                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("?????? ?????????"),
                        fieldWithPath("data.location").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("?????? ????????? ?????????"),
                        fieldWithPath("data.profileText").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.scrapPostingList").type(JsonFieldType.ARRAY).description("????????? ?????????"),
                        fieldWithPath("data.followingList").type(JsonFieldType.ARRAY).description("????????? ??????"),
                        fieldWithPath("data.followerList").type(JsonFieldType.ARRAY).description("????????? ??????")
                    )
                )
            ));
    }

    @Test
    public void getMemberTest() throws Exception {
        // given
        long memberId = 1L;
        MemberDto.Response response =
            new MemberDto.Response(
                1L,
                "taebong98",
                "taebong98",
                "??????????????????",
                null,
                "????????????",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());

        // when
        given(memberService.findMember(memberId)).willReturn(new Member());
        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(response);

        ResultActions actions = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/members/{member-id}", memberId)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions // get????????? ????????? response ??? ????????? ??????
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.memberId").value(response.getMemberId()))
            .andExpect(jsonPath("$.data.userName").value(response.getUserName()))
            .andExpect(jsonPath("$.data.email").value(response.getEmail()))
            .andExpect(jsonPath("$.data.location").value(response.getLocation()))
            .andExpect(jsonPath("$.data.profileImage").value(response.getProfileImage()))
            .andExpect(jsonPath("$.data.profileText").value(response.getProfileText()))
            .andDo(document(
                "get-member",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                pathParameters(
                    List.of(
                        parameterWithName("member-id").description("?????? ?????????")
                    )
                ),
                responseFields(
                    List.of(
                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????????"),
                        fieldWithPath("data.userName").type(JsonFieldType.STRING).description("?????????"),
                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("?????????"),
                        fieldWithPath("data.location").type(JsonFieldType.STRING).description("??????"),
                        fieldWithPath("data.profileImage").type(JsonFieldType.NULL).description("???????????????"),
                        fieldWithPath("data.profileText").type(JsonFieldType.STRING).description("????????????"),
                        fieldWithPath("data.scrapPostingList").type(JsonFieldType.ARRAY).description("???????????? ?????????"),
                        fieldWithPath("data.followingList").type(JsonFieldType.ARRAY).description("????????? ??????"),
                        fieldWithPath("data.followerList").type(JsonFieldType.ARRAY).description("????????? ??????")
                    )
                )
            ));
    }

    @Test
    public void searchMemberTest() throws Exception {

        long memberId1 = 1L;
        long memberId2 = 2L;

        Member member1 = new Member();
        member1.setMemberId(memberId1);
        member1.setUserName("??????");
        member1.setEmail("oheadnah");
        member1.setLocation("??????????????????");
        member1.setProfileImage("profileImage.jpeg");
        member1.setProfileText("????????????");

        Member member2 = new Member();
        member2.setMemberId(memberId2);
        member2.setUserName("??????2");
        member2.setEmail("oheadnah2");
        member2.setLocation("??????????????????");
        member2.setProfileImage("profileImage.jpeg");
        member2.setProfileText("????????????");


        Page<Member> pageMember = new PageImpl<>(List.of(member1, member2));
        List<Member> listMember = List.of(member1,member2);

        given(memberService.findUserName(Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt())).willReturn(pageMember);
        given(mapper.memberPageToMemberList(pageMember)).willReturn(listMember);
        given(mapper.memberDtoResponseList(listMember)).willReturn(
            List.of(
                new Response(
                    listMember.get(0).getMemberId(),
                    listMember.get(0).getUserName(),
                    listMember.get(0).getEmail(),
                    listMember.get(0).getLocation(),
                    listMember.get(0).getProfileImage(),
                    listMember.get(0).getProfileText(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>()),
                new Response(
                    listMember.get(1).getMemberId(),
                    listMember.get(1).getUserName(),
                    listMember.get(1).getEmail(),
                    listMember.get(1).getLocation(),
                    listMember.get(1).getProfileImage(),
                    listMember.get(1).getProfileText(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>())
            )
        );

        ResultActions resultActions = mockMvc.perform(
            get("/members")
                .param("search","???")
                .param("page", "1")
                .param("size","10")
                .header("Authorization", "Bear AccessToken")
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].memberId").value(listMember.get(0).getMemberId()))
            .andExpect(jsonPath("$.data[0].userName").value(listMember.get(0).getUserName()))
            .andExpect(jsonPath("$.data[0].email").value(listMember.get(0).getEmail()))
            .andExpect(jsonPath("$.data[0].location").value(listMember.get(0).getLocation()))
            .andExpect(jsonPath("$.data[0].profileImage").value(listMember.get(0).getProfileImage()))
            .andExpect(jsonPath("$.data[0].profileText").value(listMember.get(0).getProfileText()))
            .andDo(document("get-search-member",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestParameters(
                    parameterWithName("page").description("????????? ????????? ??????"),
                    parameterWithName("size").description("????????? ????????? ??????"),
                    parameterWithName("search").description("????????? ??????")
                ),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer AccessToken")
                ),
                responseFields(
                    fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                    fieldWithPath("data[].userName").type(JsonFieldType.STRING).description("?????? ?????????"),
                    fieldWithPath("data[].email").type(JsonFieldType.STRING).description("?????? ?????????"),
                    fieldWithPath("data[].location").type(JsonFieldType.STRING).description("??????"),
                    fieldWithPath("data[].profileImage").type(JsonFieldType.STRING).description("???????????????"),
                    fieldWithPath("data[].profileText").type(JsonFieldType.STRING).description("????????????"),
                    fieldWithPath("data[].scrapPostingList").type(JsonFieldType.ARRAY).description("???????????? ?????????"),
                    fieldWithPath("data[].followingList").type(JsonFieldType.ARRAY).description("????????? ??????"),
                    fieldWithPath("data[].followerList").type(JsonFieldType.ARRAY).description("????????? ??????"),
                    fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("????????? ??????"),
                    fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                    fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                    fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("?????? ????????? ???"),
                    fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("?????? ????????? ???")
                )
            ));
    }

    @Test
    public void logoutMemberTest() throws Exception {

        given(memberService.findTokenMemberEmail(Mockito.anyString())).willReturn("email");
        doNothing().when(jwtTokenizer).deleteToken(Mockito.anyString());

        ResultActions actions = mockMvc.perform(
            get("/members/logouts")
                .header("Authorization", "Bearer AccessToken")
        );

        actions
            .andExpect(status().isOk())
            .andDo(document(
                "logout-member",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer AccessToken")
                )
            ));
    }

    @Test
    public void reissuesMemberTest() throws Exception {

        given(memberService.findTokenMember(Mockito.anyString())).willReturn(new Member());
        given(jwtTokenizer.reissueAtk(Mockito.any())).willReturn("access token");

        ResultActions actions = mockMvc.perform(
            post("/members/reissues")
                .header("Refresh" , "refresh token")
        );

        actions
            .andExpect(status().isOk())
            .andDo(document(
                "reissues-member",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Refresh").description("refresh token")
                ),
                responseHeaders(
                    headerWithName("Authorization").description("Bearer AccessToken")
                )));

    }

    @Test
    public void deleteMemberTest() throws Exception {
        // given
        long memberId = 1L;
        doNothing().when(memberService).deleteMember(memberId, "token");

        // when
        ResultActions actions = mockMvc.perform(
            RestDocumentationRequestBuilders.delete("/members/{member-id}", memberId)
                .header("Authorization", "Bearer AccessToken")
        );

        // then
        actions
            .andExpect(status().isNoContent())
            .andDo(document(
                "delete-member",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                pathParameters(
                    parameterWithName("member-id").description("?????? ?????????")
                ),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer AccessToken")
                )
            ));
    }


    @Test
    public void isExistMemberTest() throws Exception {
        // given
        long memberId1 = 1L;

        Member member = new Member();
        member.setMemberId(memberId1);
        member.setUserName("nickName");
        member.setEmail("taebong");
        member.setLocation("??????????????????");
        member.setProfileImage("profileImage.jpeg");
        member.setProfileText("????????????");


        given(memberService.findMemberEmail(Mockito.anyString())).willReturn(true);

        // when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/members/existences")
                        .param("email", "taebong")
        );

        // then
        actions.andExpect(status().isOk())
                .andDo(document(
                                "check-duplicate-email",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestParameters(
                                        parameterWithName("email").description("userId (email)")
                                ),
                                responseFields(fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("????????? true, ????????? false"))
                        )
                );
    }
}