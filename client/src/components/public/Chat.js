import styled from "styled-components";
import Search from "./Search";

const StyledChat = styled.div`
  position: fixed;
  right: 0px;
  width: 400px;
  height: 100%;
  border-left: 1px solid #dbdbdb;
`;

const Chat = () => {
  return (
    <StyledChat>
      <Search label={"검색"} />
    </StyledChat>
  );
};

export default Chat;
