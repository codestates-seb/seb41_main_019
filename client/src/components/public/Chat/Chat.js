import { useState, useEffect } from "react";
import styled from "styled-components";
import Input from "../Input";
import ChatRooms from "./ChatRooms";
import Chatting from "./Chatting";
import Friends from "./Friends";
import axios from "axios";
import Cookie from "../../../util/Cookie";

const StyledChat = styled.div`
  display: flex;
  flex-direction: column;
  width: 350px;
  height: 100%;
  padding: 21px 20px 0px 20px;
  gap: 20px;

  > p {
    font-size: 18px;
    font-weight: 600;
    margin: 0px;
  }

  > p::before {
    content: "";
    display: block;
    width: 400px;
    margin: 0px 0px 20px -20px;
    border-top: 1px solid #dbdbdb;
  }

  > .chat-area::before {
    content: "";
    display: block;
    width: 400px;
    margin: 0px 0px 20px -20px;
    border-top: 1px solid #dbdbdb;
  }
`;

const Chat = () => {
  const [curChat, setCurChat] = useState(false);
  const [rooms, setRooms] = useState([]);
  const cookie = new Cookie();

  useEffect(() => {
    axios({
      method: "get",
      url: `http://13.124.33.113:8080/chatroom/${cookie.get("memberId")}`,
      headers: { Authorization: cookie.get("authorization") }
    }).then(res => {
      setRooms(res.data);
    })
  }, [])
  
  return (  
    <>
      <StyledChat>
        <Input label={"채팅"} />
        <button onClick={() => setCurChat(!curChat)}>x</button>
        {
          !curChat ? 
          <>
            <ChatRooms rooms={rooms} setCurChat={setCurChat} />
            <Friends />
          </>
          : <Chatting curChat={curChat} />
        }
      </StyledChat>
    </>
  );
};

export default Chat;
