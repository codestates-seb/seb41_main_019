package com.main19.server.member;

import static com.main19.server.utils.DocumentUtils.getRequestPreProcessor;
import static com.main19.server.utils.DocumentUtils.getResponsePreProcessor;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.main19.server.domain.member.entity.Member;
import com.main19.server.global.auth.dto.LoginDto;
import com.main19.server.domain.member.service.MemberService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriHost = "increaf.site")
@ActiveProfiles("server")
public class LoginRestDocs {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private MemberService memberService;

    @Test
    public void LoginTest() throws Exception {

        Member member = new Member(
                1L,
                "logintest",
                "logintest",
                "profileImage",
                "자기소개",
                "codestates",
                "12345678a!",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());

        LoginDto loginDto = new LoginDto();
        loginDto.setEmail(member.getEmail());
        loginDto.setPassword("12345678a!");

        String content = gson.toJson(loginDto);

        ResultActions actions = mockMvc.perform(
            post("/member")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );

        actions.andExpect(status().isOk())
            .andDo(document(
                "login",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestFields(
                    List.of(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                    )
                ),
                responseHeaders(
                    List.of(
                        headerWithName("Authorization").description("Bearer accessToken"),
                        headerWithName("Refresh").description("refreshToken")
                    )
                )
            ));
    }
}
