package com.main19.server.chat.repository;

import com.main19.server.chat.entitiy.Chat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRepository extends JpaRepository<Chat,Long> {
    List<Chat> findAllByChatRoom_ChatRoomId(long chatRoomId);
}
