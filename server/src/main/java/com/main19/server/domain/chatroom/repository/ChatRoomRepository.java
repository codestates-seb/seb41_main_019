package com.main19.server.domain.chatroom.repository;


import com.main19.server.domain.chatroom.entity.ChatRoom;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

    ChatRoom findById(long chatRoomId);
    @Query(value = "SELECT * FROM chat_room WHERE receiver_id = :num OR sender_id = :num", nativeQuery = true)
    List<ChatRoom> findByReceiverIdOrSenderId(@Param("num") long memberId);

    @Query(value = "SELECT * FROM chat_room WHERE receiver_id = :num1 AND sender_id = :num2 UNION SELECT * FROM chat_room WHERE receiver_id = :num2 AND sender_id = :num1", nativeQuery = true)
    ChatRoom findChatRoom(@Param("num1") long receiverId, @Param("num2") long senderId);

}
