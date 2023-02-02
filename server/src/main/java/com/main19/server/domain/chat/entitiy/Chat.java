package com.main19.server.domain.chat.entitiy;

import com.main19.server.domain.chatroom.entity.ChatRoom;
import com.main19.server.domain.member.entity.Member;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "receiverId")
    private Member receiver;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "senderId")
    private Member sender;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "chatRoomId")
    private ChatRoom chatRoom;
    @Column(nullable = false , columnDefinition = "Text")
    private String chat;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Chat(Member receiver, Member sender, String chat) {
        this.receiver = receiver;
        this.sender = sender;
        this.chat = chat;
    }
}
