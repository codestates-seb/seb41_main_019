package com.main19.server.chatroom;

import static com.main19.server.utils.DocumentUtils.getRequestPreProcessor;
import static com.main19.server.utils.DocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.main19.server.utils.LoginUserArgumentResolver;
import com.main19.server.chatroom.controller.ChatRoomController;
import com.main19.server.chatroom.dto.ChatRoomDto;
import com.main19.server.chatroom.entity.ChatRoom;
import com.main19.server.chatroom.mapper.ChatRoomMapper;
import com.main19.server.chatroom.service.ChatRoomService;
import com.main19.server.member.entity.Member;
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

@WebMvcTest(value = ChatRoomController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class ChatRoomRestDocs {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ChatRoomService chatRoomService;
    @MockBean
    private ChatRoomMapper chatRoomMapper;
    @Autowired
    private Gson gson;
    @MockBean
    private LoginUserArgumentResolver loginUserArgumentResolver;

    @Test
    public void PostChatRoomTest() throws Exception {

        long chatRoomId = 1L;
        long memberId1 = 1L;
        long memberId2 = 2L;

        ChatRoomDto.Post post = new ChatRoomDto.Post(memberId1,memberId2);

        String content = gson.toJson(post);

        ChatRoomDto.Response response =
            new ChatRoomDto.Response(
                chatRoomId,
                memberId1,
                memberId2);

        given(chatRoomMapper.chatRoomPostDtoToChatRoom(Mockito.any(ChatRoomDto.Post.class)))
            .willReturn(new ChatRoom());

        given(chatRoomService.createChatRoom(Mockito.any(ChatRoom.class), Mockito.anyLong(),
            Mockito.anyLong(), Mockito.any()))
            .willReturn(new ChatRoom());

        given(chatRoomMapper.chatRoomToChatRoomResponseDto(Mockito.any(ChatRoom.class))).willReturn(
            response);

        ResultActions actions =
            mockMvc.perform(
                post("/chatroom")
                    .header("Authorization", "Bearer AccessToken")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            );

        actions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.receiverId").value(post.getReceiverId()))
            .andExpect(jsonPath("$.data.senderId").value(post.getSenderId()))
            .andDo(document(
                "post-chat-room",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer AccessToken")
                ),
                requestFields(
                    List.of(
                        fieldWithPath("receiverId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                        fieldWithPath("senderId").type(JsonFieldType.NUMBER).description("회원 식별자")
                    )
                ),
                responseFields(
                    fieldWithPath("data.chatRoomId").type(JsonFieldType.NUMBER).description("채팅방 식별자"),
                    fieldWithPath("data.receiverId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("data.senderId").type(JsonFieldType.NUMBER).description("회원 식별자")
                )
            ));
    }

    @Test
    public void GetChatRoomTest() throws Exception {

        long memberId1 = 1L;
        long memberId2 = 2L;
        long memberId3 = 3L;

        Member member1 = new Member();
        member1.setMemberId(memberId1);
        Member member2 = new Member();
        member2.setMemberId(memberId2);
        Member member3 = new Member();
        member3.setMemberId(memberId3);

        ChatRoom chatRoom1 = new ChatRoom(member1,member2);
        chatRoom1.setChatRoomId(1L);
        chatRoom1.setReceiver(member1);
        chatRoom1.setSender(member2);

        ChatRoom chatRoom2 = new ChatRoom(member1,member3);
        chatRoom2.setChatRoomId(2L);
        chatRoom2.setReceiver(member1);
        chatRoom2.setSender(member3);

        List<ChatRoom> list = List.of(chatRoom1,chatRoom2);

        given(chatRoomService.findAllChatRoom(Mockito.anyLong(),Mockito.any()))
            .willReturn(list);

        given(chatRoomMapper.chatRoomToChatRoomDtoResponse(Mockito.anyList())).willReturn(
            List.of(
                new ChatRoomDto.Response(
                    list.get(0).getChatRoomId(),
                    list.get(0).getReceiver().getMemberId(),
                    list.get(0).getSender().getMemberId()),
                new ChatRoomDto.Response(
                    list.get(1).getChatRoomId(),
                    list.get(1).getReceiver().getMemberId(),
                    list.get(1).getSender().getMemberId())));

        ResultActions actions =
            mockMvc.perform(
                get("/chatroom/{member-id}", memberId1)
                    .header("Authorization", "Bearer AccessToken")
                    .accept(MediaType.APPLICATION_JSON)
            );

        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].chatRoomId").value(list.get(0).getChatRoomId()))
            .andExpect(jsonPath("$.[0].receiverId").value(list.get(0).getReceiver().getMemberId()))
            .andExpect(jsonPath("$.[0].senderId").value(list.get(0).getSender().getMemberId()))
            .andExpect(jsonPath("$.[1].chatRoomId").value(list.get(1).getChatRoomId()))
            .andExpect(jsonPath("$.[1].receiverId").value(list.get(1).getReceiver().getMemberId()))
            .andExpect(jsonPath("$.[1].senderId").value(list.get(1).getSender().getMemberId()))
            .andDo(document(
                "get-chat-room",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer AccessToken")
                ),
                pathParameters(
                    parameterWithName("member-id").description("회원 식별자")
                ),
                responseFields(
                    fieldWithPath("[0].chatRoomId").type(JsonFieldType.NUMBER).description("채팅방 식별자"),
                    fieldWithPath("[0].receiverId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("[0].senderId").type(JsonFieldType.NUMBER).description("회원 식별자")
                )
            ));
    }
}
