package com.main19.server.chat.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.main19.server.chat.dto.ChatDto;
import com.main19.server.chat.entitiy.Chat;
import com.main19.server.chat.mapper.ChatMapper;
import com.main19.server.chat.service.ChatService;
import java.io.IOException;
import java.util.HashMap;
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

        ObjectMapper mapper = new ObjectMapper();
        template.convertAndSend("/sub/chat/" + chat.getChatRoomId(), response);
    }
}
