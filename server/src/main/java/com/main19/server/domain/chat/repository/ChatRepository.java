package com.main19.server.domain.chat.repository;

import com.main19.server.domain.chat.entitiy.Chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRepository extends JpaRepository<Chat,Long> {
    Page<Chat> findAllByChatRoom_ChatRoomId(long chatRoomId, Pageable pageable);
}
