import * as StompJs from '@stomp/stompjs';
import { soltChat } from './soltChat';

export const client = new StompJs.Client({
    brokerURL : "ws://13.124.33.113:8080/chat",
    debug : (e) => {
        // console.log(e);
    }
})

export const connect = () => {  
    client.activate()
}

export const subscribe = (curChat, log, setLog) => {
    client.subscribe(`/sub/chat/${curChat.chatRoomId}`, (body) => {
        const json = JSON.parse(body.body)
        const data = {
            receiverId: parseInt(json.receiverId),
            senderId: parseInt(json.senderId),
            chat: json.chat,
            createdAt: json.createdAt.split(".")[0]
        }

        console.log(data);
        const [preLog] = log.slice();
        preLog.push(data)
        setLog(soltChat(preLog));
    });
}

export const send = (curChat, user, message) => {
    client.publish({
        destination: `/pub/message`,
        body: JSON.stringify({
            chatRoomId: curChat.chatRoomId,
            receiverId: curChat.senderId === user ? curChat.receiverId : curChat.senderId,
            senderId: curChat.senderId === user ? curChat.senderId : user,
            chat: message
        })
    })
}

export const disConnect = () => {
    client.deactivate();
}