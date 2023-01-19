import { useState } from "react";
import styled from "styled-components";
import Input from "../Input";
import ChatRooms from "./ChatRooms";
import Chatting from "./Chatting";
import Friends from "./Friends";
import { useEffect } from "react";
import axios from "axios";
import Cookie from "../../../util/Cookie";
import { RiContactsBookLine } from "react-icons/ri";

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
  
  return (  
    <>
      <StyledChat>
        <Input label={"채팅"} />
        <button onClick={() => setCurChat(!curChat)}>x</button>
        {curChat ? (
          <>
            <Chatting />
            <ChatRooms />
          </>
        ) : (
          <>
            <ChatRooms />
            <Friends />
          </>
        )}
      </StyledChat>
    </>
  );
};

export default Chat;
