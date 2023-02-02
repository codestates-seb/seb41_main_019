package com.main19.server.domain.chat.service;

import com.main19.server.global.auth.jwt.JwtTokenizer;
import com.main19.server.domain.chat.dto.ChatDto;
import com.main19.server.domain.chat.entitiy.Chat;
import com.main19.server.domain.chat.repository.ChatRepository;
import com.main19.server.domain.chatroom.entity.ChatRoom;
import com.main19.server.domain.chatroom.service.ChatRoomService;
import com.main19.server.global.exception.BusinessLogicException;
import com.main19.server.global.exception.ExceptionCode;
import com.main19.server.domain.member.service.MemberService;
import com.main19.server.domain.sse.entity.Sse.SseType;
import com.main19.server.domain.sse.service.SseService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final ChatRepository chatRepository;
    private final MemberService memberService;
    private final ChatRoomService chatRoomService;
    private final JwtTokenizer jwtTokenizer;
    private final SseService sseService;

    public Chat createChat(Chat chat, long receiverId, long senderId, long roomId) {

        ChatRoom chatRoom = chatRoomService.findChatRoom(roomId);

        if(chatRoom.getReceiverId() != receiverId && chatRoom.getSenderId() != receiverId) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        if(chatRoom.getReceiverId() != senderId && chatRoom.getSenderId() != senderId) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        chat.setReceiver(memberService.findMember(receiverId));
        chat.setSender(memberService.findMember(senderId));
        chat.setChatRoom(chatRoom);

        sseService.sendChatRoom(memberService.findMember(receiverId), SseType.message, memberService.findMember(senderId));

        return chatRepository.save(chat);
    }

    @Transactional(readOnly = true)
    public Page<Chat> findAllChat(long chatRoomId, String token, int page, int size) {

        if (chatRoomService.findChatRoom(chatRoomId).getReceiverId() != jwtTokenizer.getMemberId(token)
            && chatRoomService.findChatRoom(chatRoomId).getSenderId() != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        return chatRepository.findAllByChatRoom_ChatRoomId(chatRoomId, PageRequest.of(page, size, Sort.by("chatId").descending()));
    }

    public List<ChatDto.Response> changeList(List<ChatDto.Response> chat) {

        List<ChatDto.Response> response = new ArrayList<>();

        for(int i=0; i<chat.size(); i++) {
            response.add(chat.get(chat.size()-i-1));
        }

        return response;
    }


}
