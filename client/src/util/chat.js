import * as StompJs from '@stomp/stompjs';
import { soltChat } from './soltChat';

export const client = new StompJs.Client({
    brokerURL : "ws://13.124.33.113:8080/chat",
    debug : (e) => {
        console.log(e);
    }
})

export const connect = () => {  
    client.activate()
}

export const subscribe = (curChat, log, setLog) => {
    console.log(log);
    client.subscribe(`/sub/chat/${curChat.chatRoomId}`, (body) => {
        const json = JSON.parse(body.body)
        const data = {
            receiverId: parseInt(json.receiverId),
            senderId: parseInt(json.senderId),
            chat: json.chat,
            createdAt: new Date().toISOString().slice(0, -5)
        }
        const [preLog] = log.slice();
        preLog.push(data)
        setLog(soltChat(preLog));
    });
}

export const send = (curChat, message) => {
    client.publish({
        destination: `/pub/message`,
        body: JSON.stringify({
            chatRoomId: curChat.chatRoomId,
            receiverId: curChat.receiverId,
            senderId: curChat.senderId,
            chat: message
        })
    })
}

export const disConnect = () => {
    client.deactivate();
}