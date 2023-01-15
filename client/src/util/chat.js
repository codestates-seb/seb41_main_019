import SockJS from 'sockjs-client';
import StompJS, { Stomp } from '@stomp/stompjs';

class Client {
    constructor() {
        this.socket = new SockJS("http://13.124.33.113:8080/chat");
        this.stomp = Stomp.over(this.socket);
        // this.stomp.configure({
        //     reconnectDelay: 5000
        // })
    }

    connect() {
        this.stomp.connect({}, (frame) => {
            console.log(frame);
        });
    }

    subscribe(idt) {
        this.stomp.subscribe(`/sub/chat/${idt}`, (frame) => {
            console.log(frame);
        })
    }

    unsub(idt) {
        this.stomp.subscribe(`/sub/chat/${idt}`, (frame) => {
            console.log(frame);
        }).unsubscribe();
    }

    send(idt, message) {
        this.stomp.send(`/sub/chat/${idt}`, {}, JSON.stringify(message));
    }

    disconnect() {
        this.stomp.disconnect();
    }
}

export default Client;