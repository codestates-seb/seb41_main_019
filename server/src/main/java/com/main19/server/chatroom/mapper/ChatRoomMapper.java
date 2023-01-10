package com.main19.server.chatroom.mapper;

import com.main19.server.chatroom.dto.ChatRoomDto;
import com.main19.server.chatroom.entity.ChatRoom;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {

    ChatRoom chatRoomPostDtoToChatRoom(ChatRoomDto.Post post);

    @Mapping(source = "receiver.memberId" , target = "receiverId")
    @Mapping(source = "sender.memberId" , target = "senderId")
    ChatRoomDto.Response chatRoomToChatRoomResponseDto(ChatRoom chatRoom);
    List<ChatRoomDto.Response> chatRoomToChatRoomDtoResponse(List<ChatRoom> chatroom);
}
