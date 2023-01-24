import { useState, useEffect } from "react";
import Cookie from "../../../util/Cookie";
import axios from "axios";
import { soltChat } from "../../../util/soltChat"

export const useChat = (curChat) => {
    const [ log, setLog ] = useState([]);
    const cookie = new Cookie();

    useEffect(() => {
        axios({
          method: "get",
          url: `http://13.124.33.113:8080/message/${curChat.chatRoomId}?page=1&size=15`,
          headers: { Authorization: cookie.get("authorization") }
        }).then(res => {
          console.log(res.data);
          setLog(soltChat(res.data)); 
        }).catch(e => {
          console.log(e);
        })
    }, [])

    return [ log, setLog ];
}