package com.main19.server.domain.chatroom.controller;

import com.main19.server.domain.chatroom.dto.ChatRoomDto;
import com.main19.server.domain.chatroom.entity.ChatRoom;
import com.main19.server.domain.chatroom.mapper.ChatRoomMapper;
import com.main19.server.domain.chatroom.service.ChatRoomService;
import com.main19.server.global.dto.SingleResponseDto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatRoomMapper chatRoomMapper;

    @PostMapping
    public ResponseEntity postChatRoom (@RequestHeader(name = "Authorization") String token,
        @Valid @RequestBody ChatRoomDto.Post requestBody) {

        ChatRoom chatRoom = chatRoomMapper.chatRoomPostDtoToChatRoom(requestBody);
        ChatRoom response = chatRoomService.createChatRoom(chatRoom, requestBody.getReceiverId(),
            requestBody.getSenderId(), token);

        return new ResponseEntity(
            new SingleResponseDto<>(chatRoomMapper.chatRoomToChatRoomResponseDto(response)),
            HttpStatus.CREATED);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getChatRoom(@RequestHeader(name = "Authorization") String token,
        @PathVariable("member-id") @Positive long memberId) {

        List<ChatRoom> chatRooms = chatRoomService.findAllChatRoom(memberId,token);
        List<ChatRoomDto.Response> response = chatRoomMapper.chatRoomToChatRoomDtoResponse(
            chatRooms);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PatchMapping("/{chatroom-id}")
    public ResponseEntity patchChatRoom(@RequestHeader(name = "Authorization") String token,
        @PathVariable("chatroom-id") @Positive long chatRoomId, @Valid @RequestBody ChatRoomDto.Patch requestBody) {

        ChatRoom chatRoom = chatRoomService.updateChatRoom(chatRoomId, requestBody.getMemberId(),token);
        ChatRoomDto.Response response = chatRoomMapper.chatRoomToChatRoomResponseDto(chatRoom);

        return new ResponseEntity(new SingleResponseDto<>(response),HttpStatus.OK);
    }

    @DeleteMapping("/{chatroom-id}")
    public ResponseEntity deleteChatRoom(@RequestHeader(name = "Authorization") String token,
        @PathVariable("chatroom-id") @Positive long chatRoomId) {

        chatRoomService.deleteChatRoom(chatRoomId,token);

        return ResponseEntity.noContent().build();
    }
}
