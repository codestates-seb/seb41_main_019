import * as StompJs from '@stomp/stompjs';
import { json } from 'react-router-dom';

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


export const subscribe = (idt) => {
    client.subscribe(`/sub/chat/${idt}`, (frame) => {
        console.log(frame);
    })
}

export const send = (idt, message, id) => {
    client.publish({
        destination: `/sub/chat${idt}`,
        body: JSON.stringify({
            id : id,
            chat : message
        })
    })
}

export const disConnect = () => {
    client.deactivate();
}