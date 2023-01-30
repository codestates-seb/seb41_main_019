import * as StompJs from '@stomp/stompjs';

export const client = new StompJs.Client({
    brokerURL : `${process.env.REACT_APP_CHAT}`,
    debug : (e) => {
        console.log(e);
    }
})

export const connect = () => {  
    client.activate()
}

export const subscribe = (curChat, setRes) => {
    client.subscribe(`/sub/chat/${curChat.chatRoomId}`, (body) => {
        const json = JSON.parse(body.body)
        const data = {
            receiverId: parseInt(json.receiverId),
            senderId: parseInt(json.senderId),
            chat: json.chat,
            createdAt: json.createdAt.split(".")[0]
        }

        setRes(data);
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