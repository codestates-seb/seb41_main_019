import * as StompJs from '@stomp/stompjs';

const client = new StompJs.Client({
    brokerURL : "ws://13.124.33.113:8080/chat",
    debug : (e) => {
        console.log(e);
    }
})

export const connect = () => {
    client.activate();
}


export const subscribe = (curChat) => {
    client.subscribe(`/sub/chat/${curChat}`, (body) => {
        console.log(body);
    });
}

export const send = (curChat, receiverId, senderId, message) => {
    const body = {
        chatRoomId: curChat,
        receiverId,
        senderId,
        chat: message
    }

    client.publish({
        destination: `/pub/message`,
        body: body
    })

    console.log(body);
}

export const disConnect = () => {
    client.deactivate();
}