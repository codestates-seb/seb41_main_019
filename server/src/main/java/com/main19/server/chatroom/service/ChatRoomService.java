package com.main19.server.chatroom.service;

import com.main19.server.auth.jwt.JwtTokenizer;
import com.main19.server.chatroom.entity.ChatRoom;
import com.main19.server.chatroom.repository.ChatRoomRepository;
import com.main19.server.exception.BusinessLogicException;
import com.main19.server.exception.ExceptionCode;
import com.main19.server.member.entity.Member;
import com.main19.server.member.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberService memberService;
    private final JwtTokenizer jwtTokenizer;

    public ChatRoom createChatRoom(ChatRoom chatRoom, long receiverId, long senderId, String token) {

        if (senderId != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        if(chatRoomRepository.findChatRoom(receiverId,senderId) != null) {
            throw new BusinessLogicException(ExceptionCode.CHATROOM_EXISTS);
        }

        Member receiver = memberService.findMember(receiverId);
        Member sender = memberService.findMember(senderId);
        chatRoom.setReceiver(receiver);
        chatRoom.setSender(sender);

        return chatRoomRepository.save(chatRoom);
    }

    @Transactional(readOnly = true)
    public ChatRoom findChatRoom(long chatRoomId) {

        return chatRoomRepository.findById(chatRoomId);
    }

    @Transactional(readOnly = true)
    public List<ChatRoom> findAllChatRoom(long memberId, String token) {

        if (memberId != jwtTokenizer.getMemberId(token)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }

        return chatRoomRepository.findByReceiverIdOrSenderId(memberId);
    }

}
