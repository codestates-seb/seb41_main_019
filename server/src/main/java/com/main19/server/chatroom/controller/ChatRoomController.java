package com.main19.server.chatroom.controller;

import com.main19.server.chatroom.dto.ChatRoomDto;
import com.main19.server.chatroom.dto.ChatRoomDto.Response;
import com.main19.server.chatroom.entity.ChatRoom;
import com.main19.server.chatroom.mapper.ChatRoomMapper;
import com.main19.server.chatroom.repository.ChatRoomRepository;
import com.main19.server.chatroom.service.ChatRoomService;
import com.main19.server.dto.SingleResponseDto;
import java.util.List;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatRoomMapper chatRoomMapper;

    @PostMapping
    public ResponseEntity postChatRoom(@RequestBody ChatRoomDto.Post requestBody) {

        ChatRoom chatRoom = chatRoomMapper.chatRoomPostDtoToChatRoom(requestBody);
        ChatRoom response = chatRoomService.createChatRoom(chatRoom, requestBody.getReceiverId(),
            requestBody.getSenderId());

        return new ResponseEntity(new SingleResponseDto<>(chatRoomMapper.chatRoomToChatRoomResponseDto(response)),HttpStatus.CREATED);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getChatRoom(@PathVariable("member-id") @Positive long memberId) {

        List<ChatRoom> chatRooms = chatRoomService.findAllChatRoom(memberId);
        List<ChatRoomDto.Response> response = chatRoomMapper.chatRoomToChatRoomDtoResponse(chatRooms);

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
