import styled from "styled-components";

const StyledChatting = styled.div`
  ::before {
    content: "";
    display: block;
    width: 400px;
    margin: 0px 0px 0px -20px;
    border-top: 1px solid #dbdbdb;
  }

  width: 100%;
  height: 400px;

  p {
    font-size: 18px;
    font-weight: 600;
  }
`;
const Chatting = () => {
  return (
    <StyledChatting>
      <p>최근 채팅</p>
    </StyledChatting>
  );
};

export default Chatting;
