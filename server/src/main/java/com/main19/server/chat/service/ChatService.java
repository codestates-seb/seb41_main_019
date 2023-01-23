package com.main19.server.chat.service;

import com.main19.server.auth.jwt.JwtTokenizer;
import com.main19.server.chat.entitiy.Chat;
import com.main19.server.chat.repository.ChatRepository;
import com.main19.server.chatroom.service.ChatRoomService;
import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.member.service.MemberService;
import com.main19.server.sse.entity.Sse.SseType;
import com.main19.server.sse.service.SseService;
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

        chat.setReceiver(memberService.findMember(receiverId));
        chat.setSender(memberService.findMember(senderId));
        chat.setChatRoom(chatRoomService.findChatRoom(roomId));

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


}
