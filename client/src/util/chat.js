import * as StompJs from '@stomp/stompjs';

//sub/chat/

const client = new StompJs.Client({
    brokerURL : "ws://13.124.33.113:8080/chat",
    debug : (e) => {
        console.log(e);
    }
})

export const connect = () => {
    client.activate();
}


export const subscribe = (idt, func) => {
    client.subscribe(`/pub/chat/${idt}`, func);
}

export const send = (idt, receiverId, senderId, message) => {
    client.publish({
        destination: `/pub/chat/${idt}`,
        body: JSON.stringify({
            receiverId,
            senderId,
            chat : message
        })
    })
}

export const disConnect = () => {
    client.deactivate();
}