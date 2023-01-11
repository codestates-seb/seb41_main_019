import styled from "styled-components";
import Search from "./Search";
import Chatting from "./Chatting";

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
`;

const Chat = () => {
  return (
    <StyledChat>
      <Search label={"채팅"} />
      <Chatting />
    </StyledChat>
  );
};

export default Chat;
