package com.main19.server.chatroom.service;

import com.main19.server.chatroom.entity.ChatRoom;
import com.main19.server.chatroom.repository.ChatRoomRepository;
import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.member.entity.Member;
import com.main19.server.member.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberService memberService;

    public ChatRoom createChatRoom(ChatRoom chatRoom, long receiverId, long senderId) {

        if(chatRoomRepository.findSenderChatRoom(receiverId,senderId) != null) {
            throw new BusinessLogicException(ExceptionCode.CHATROOM_EXISTS);
        }

        if(chatRoomRepository.findReceiverChatRoom(receiverId,senderId) != null) {
            throw new BusinessLogicException(ExceptionCode.CHATROOM_EXISTS);
        }

        Member receiver = memberService.findMember(receiverId);
        Member sender = memberService.findMember(senderId);
        chatRoom.setReceiver(receiver);
        chatRoom.setSender(sender);

        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoom findChatRoom(long chatRoomId) {

        return chatRoomRepository.findById(chatRoomId);
    }

    public List<ChatRoom> findAllChatRoom(long memberId) {
        return chatRoomRepository.findByReceiverIdOrSenderId(memberId);
    }

}
