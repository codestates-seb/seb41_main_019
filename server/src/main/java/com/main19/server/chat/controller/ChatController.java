package com.main19.server.chat.controller;

import com.main19.server.auth.Login;
import com.main19.server.chat.dto.ChatDto;
import com.main19.server.chat.entitiy.Chat;
import com.main19.server.chat.mapper.ChatMapper;
import com.main19.server.chat.service.ChatService;
import com.main19.server.chatroom.service.ChatRoomService;
import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.member.entity.Member;
import com.main19.server.member.service.MemberService;
import java.util.List;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;
    private final ChatRoomService chatRoomService;
    private final ChatMapper chatMapper;

    @MessageMapping("/chat/{roomId}")
    public void chat(@DestinationVariable long roomId, ChatDto.Post chatDto) throws Exception {

        if (chatRoomService.findChatRoom(roomId) == null) {
            throw new BusinessLogicException(ExceptionCode.CHATROOM_NOT_FOUND);
        }

        Chat chat = chatMapper.chatPostDtoToChat(chatDto);
        chatService.createChat(chat, chatDto.getReceiverId(), chatDto.getSenderId(), roomId);

        simpMessagingTemplate.convertAndSend("/sub/chat/" + roomId, chatDto);
    }

    @GetMapping("/message/{room-id}")
    public ResponseEntity getAllChat(@Login Member tokenMember,
        @PathVariable("room-id") @Positive long roomId) {

        List<Chat> chat = chatService.findAllChat(roomId,tokenMember);
        List<ChatDto.Response> response = chatMapper.chatToChatDtoResponse(chat);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
