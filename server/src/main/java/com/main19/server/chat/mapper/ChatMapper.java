package com.main19.server.chat.mapper;

import com.main19.server.chat.dto.ChatDto;
import com.main19.server.chat.entitiy.Chat;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    Chat chatPostDtoToChat(ChatDto.Post chatDto);

    List<Chat> pageChatToListChat(Page<Chat> chat);

    List<ChatDto.Response> chatToChatDtoResponse(List<Chat> chat);

    @Mapping(source = "receiver.memberId" , target = "receiverId")
    @Mapping(source = "sender.memberId" , target = "senderId")
    ChatDto.Response chatToChatResponse(Chat chat);
}