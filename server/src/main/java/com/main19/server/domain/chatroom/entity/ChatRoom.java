package com.main19.server.domain.chatroom.entity;

import com.main19.server.domain.chat.entitiy.Chat;
import com.main19.server.domain.member.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "receiverId")
    private Member receiver;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "senderId")
    private Member sender;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE)
    private List<Chat> chatList = new ArrayList<>();

    @Column
    private Long leaveId;

    public ChatRoom(Member receiver, Member sender) {
        this.receiver = receiver;
        this.sender = sender;
    }

    public long getReceiverId() {
        return receiver.getMemberId();
    }

    public long getSenderId() {
        return sender.getMemberId();
    }
}
