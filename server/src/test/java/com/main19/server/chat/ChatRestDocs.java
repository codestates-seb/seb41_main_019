package com.main19.server.chat;

import static com.main19.server.utils.DocumentUtils.getRequestPreProcessor;
import static com.main19.server.utils.DocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.main19.server.domain.chat.controller.KafkaController;
import com.main19.server.domain.chat.dto.ChatDto;
import com.main19.server.domain.chat.entitiy.Chat;
import com.main19.server.domain.chat.mapper.ChatMapper;
import com.main19.server.domain.chat.service.ChatService;
import com.main19.server.domain.chatroom.entity.ChatRoom;
import com.main19.server.domain.chatroom.service.ChatRoomService;
import com.main19.server.domain.member.entity.Member;
import java.time.LocalDateTime;
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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(value = KafkaController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(uriHost = "increaf.site")
public class ChatRestDocs {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ChatService chatService;
    @MockBean
    private ChatMapper chatMapper;
    @MockBean
    private ChatRoomService chatRoomService;;
    @MockBean
    private KafkaTemplate kafkaTemplate;

    @Test
    public void GetChatTest() throws Exception {

        long chatId1= 1L;
        long chatId2= 2L;
        long memberId1 = 1L;
        long memberId2 = 2L;

        Member member1 = new Member();
        member1.setMemberId(memberId1);
        Member member2 = new Member();
        member2.setMemberId(memberId2);

        ChatRoom chatRoom = new ChatRoom(member1,member2);
        chatRoom.setChatRoomId(1L);
        chatRoom.setReceiver(member1);
        chatRoom.setSender(member2);

        LocalDateTime createdAt = LocalDateTime.of(2023,01,01,23,59,59);

        Chat chat1 = new Chat(chatId1,member1,member2,chatRoom,"hi", createdAt);
        Chat chat2 = new Chat(chatId2,member1,member2,chatRoom,"hello", createdAt);

        Page<Chat> chat = new PageImpl<>(List.of(chat1,chat2));
        List<Chat> list = List.of(chat1,chat2);

        given(chatService.findAllChat(Mockito.anyLong(),Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt()))
            .willReturn(chat);

        given(chatMapper.pageChatToListChat(Mockito.any())).willReturn(list);

        given(chatMapper.chatToChatDtoResponse(Mockito.anyList())).willReturn(
            List.of(
                new ChatDto.Response(
                    list.get(0).getChatRoom().getChatRoomId(),
                    list.get(0).getReceiver().getMemberId(),
                    list.get(0).getSender().getMemberId(),
                    list.get(0).getChat(),
                    list.get(0).getCreatedAt()),
                new ChatDto.Response(
                    list.get(1).getChatRoom().getChatRoomId(),
                    list.get(1).getReceiver().getMemberId(),
                    list.get(1).getSender().getMemberId(),
                    list.get(1).getChat(),
                    list.get(1).getCreatedAt())));
        given(chatService.changeList(Mockito.anyList())).willReturn(List.of(new ChatDto.Response(
                        list.get(0).getChatRoom().getChatRoomId(),
                        list.get(0).getReceiver().getMemberId(),
                        list.get(0).getSender().getMemberId(),
                        list.get(0).getChat(),
                        list.get(0).getCreatedAt()),
                new ChatDto.Response(
                        list.get(1).getChatRoom().getChatRoomId(),
                        list.get(1).getReceiver().getMemberId(),
                        list.get(1).getSender().getMemberId(),
                        list.get(1).getChat(),
                        list.get(1).getCreatedAt())));

        ResultActions actions =
            mockMvc.perform(
                get("/message/{member-id}", memberId1)
                    .header("Authorization", "Bearer AccessToken")
                    .param("page","1")
                    .param("size","10")
                    .accept(MediaType.APPLICATION_JSON)
            );

        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].chatRoomId").value(list.get(0).getChatRoom().getChatRoomId()))
            .andExpect(jsonPath("$.[0].receiverId").value(list.get(0).getReceiver().getMemberId()))
            .andExpect(jsonPath("$.[0].senderId").value(list.get(0).getSender().getMemberId()))
            .andExpect(jsonPath("$.[0].chat").value(list.get(0).getChat()))
            .andExpect(jsonPath("$.[0].createdAt").value("2023-01-01T23:59:59"))
            .andExpect(jsonPath("$.[1].chatRoomId").value(list.get(1).getChatRoom().getChatRoomId()))
            .andExpect(jsonPath("$.[1].receiverId").value(list.get(1).getReceiver().getMemberId()))
            .andExpect(jsonPath("$.[1].senderId").value(list.get(1).getSender().getMemberId()))
            .andExpect(jsonPath("$.[1].chat").value(list.get(1).getChat()))
            .andExpect(jsonPath("$.[1].createdAt").value("2023-01-01T23:59:59"))
            .andDo(document(
                "get-chat",
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
                    fieldWithPath("[0].senderId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                    fieldWithPath("[0].chat").type(JsonFieldType.STRING).description("채팅 내용"),
                    fieldWithPath("[0].createdAt").type(JsonFieldType.STRING).description("채팅 시간")
                )
            ));
    }
}
