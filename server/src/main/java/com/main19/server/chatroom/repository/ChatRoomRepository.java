package com.main19.server.chatroom.repository;


import com.main19.server.chatroom.entity.ChatRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

    ChatRoom findById(long chatRoomId);
    @Query(value = "SELECT * FROM CHAT_ROOM WHERE RECEIVER_ID = :num OR SENDER_ID = :num", nativeQuery = true)
    List<ChatRoom> findByReceiverIdOrSenderId(@Param("num") long memberId);

    @Query(value = "SELECT * FROM CHAT_ROOM WHERE RECEIVER_ID = :num1 AND SENDER_ID = :num2", nativeQuery = true)
    ChatRoom findSenderChatRoom(@Param("num1") long receiverId, @Param("num2") long senderId);

    @Query(value = "SELECT * FROM CHAT_ROOM WHERE RECEIVER_ID = :num1 AND SENDER_ID = :num2", nativeQuery = true)
    ChatRoom findReceiverChatRoom(@Param("num2") long receiverId, @Param("num1") long senderId);
}
