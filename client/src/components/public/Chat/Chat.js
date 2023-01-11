import { useState } from "react";
import styled from "styled-components";
import Search from "../Search";
import ChatRooms from "./ChatRooms";
import Chatting from "./Chatting";
import Friends from "./Friends";
import { chatLogData } from "../../../assets/dummyData/chatLogData";
import { friendsData } from "../../../assets/dummyData/friendsData";

const StyledChat = styled.div`
  position: fixed;
  right: 0px;
  display: flex;
  flex-direction: column;
  width: 350px;
  height: 100%;
  border-left: 1px solid #dbdbdb;
  padding: 20px 20px 0px 20px;
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
  const [curChat, setCurChat] = useState(null);
  const [chatLog, setChatLog] = useState(chatLogData);
  const [freinds, setFreinds] = useState(friendsData);

  const handleCurChat = (value) => {
    setCurChat(value);
  };

  return (
    <>
      <StyledChat>
        <Search label={"채팅"} />
        {curChat ? (
          <>
            <Chatting
              handleCurChat={handleCurChat}
              curChat={curChat}
              chatLog={chatLog}
            />
            <ChatRooms
              handleCurChat={handleCurChat}
              chatLog={chatLog}
              freinds={freinds}
            />
          </>
        ) : (
          <>
            <ChatRooms
              handleCurChat={handleCurChat}
              chatLog={chatLog}
              freinds={freinds}
            />
            <Friends handleCurChat={handleCurChat} freinds={freinds} />
          </>
        )}
      </StyledChat>
    </>
  );
};

export default Chat;
