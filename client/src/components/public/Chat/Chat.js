import { useState } from "react";
import styled from "styled-components";
import Search from "../Search";
import Chatting from "./Chatting";
import Friends from "./Friends";

const StyledChat = styled.div`
  position: fixed;
  right: 0px;
  display: flex;
  flex-direction: column;
  width: 400px;
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
  const [isOpend, setIsOpend] = useState(true);
  return (
    <StyledChat>
      <Search label={"채팅"} />
      {isOpend ? <Chatting setIsOpend={setIsOpend} /> : null}
      <Friends setIsOpend={setIsOpend} />
    </StyledChat>
  );
};

export default Chat;
