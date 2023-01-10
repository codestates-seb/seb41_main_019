package com.main19.server.chatroom.repository;


import com.main19.server.chatroom.entity.ChatRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

    ChatRoom findById(long chatRoomId);
    @Query(value = "SELECT * FROM CHAT_ROOM WHERE RECEIVER_ID = :num OR SENDER_ID = :num", nativeQuery = true)
    List<ChatRoom> findByReceiverIdOrSenderId(@Param("num") long memberId);
}
