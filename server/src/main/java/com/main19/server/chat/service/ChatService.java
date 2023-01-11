package com.main19.server.chat.service;

import com.main19.server.chat.entitiy.Chat;
import com.main19.server.chat.repository.ChatRepository;
import com.main19.server.chatroom.service.ChatRoomService;
import com.main19.server.member.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final MemberService memberService;
    private final ChatRoomService chatRoomService;

    public Chat createChat(Chat chat, long receiverId, long senderId, long roomId) {

        chat.setReceiver(memberService.findMember(receiverId));
        chat.setSender(memberService.findMember(senderId));
        chat.setChatRoom(chatRoomService.findChatRoom(roomId));

        return chatRepository.save(chat);
    }

    public List<Chat> findAllChat(long chatRoomId) {
        return chatRepository.findAllByChatRoom_ChatRoomId(chatRoomId);
    }


}
