package com.main19.server.domain.chat.kafka;

import com.main19.server.domain.chat.dto.ChatDto;
import com.main19.server.domain.chat.entitiy.Chat;
import com.main19.server.domain.chat.mapper.ChatMapper;
import com.main19.server.domain.chat.service.ChatService;
import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final SimpMessagingTemplate template;
    private final ChatMapper chatMapper;
    private final ChatService chatService;

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.id}")
    public void consume(ChatDto.Post chat) throws IOException {
        log.info("Consumed Message : " + chat.getChat());

        Chat mappedChat = chatMapper.chatPostDtoToChat(chat);
        chatService.createChat(mappedChat, chat.getReceiverId(), chat.getSenderId(), chat.getChatRoomId());
        ChatDto.Response response = chatMapper.chatToChatResponse(mappedChat);

        template.convertAndSend("/sub/chat/" + chat.getChatRoomId(), response);
    }
}
