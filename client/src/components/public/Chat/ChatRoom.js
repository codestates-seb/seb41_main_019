import styled from "styled-components";

const StyledFriend = styled.li`
  display: flex;
  ${({ top }) => (top ? null : "border-bottom: 1px solid #dbdbdb;")}
  margin: 0px 0px 10px 0px;
  padding: 0px 0px 5px 0px;

  div:nth-of-type(1) {
    width: 10%;
    margin: 0px 10px 0px 0px;

    img {
      width: 100%;
      height: 100%;
      border-radius: 30px;
    }
  }

  div:nth-of-type(2) {
    width: 81%;
    display: flex;
    flex-direction: column;

    span {
      font-size: 14px;
      font-weight: 300;
      color: #5e8b7e;
    }

    span:last-child {
      color: black;
    }
  }
`;

const StyledButton = styled.button``;

const ChatRoom = ({ handleCurChat, freind }) => {
  return (
    <StyledFriend>
      <div>
        <img
          src="https://cdn.pixabay.com/photo/2020/05/17/20/21/cat-5183427__480.jpg"
          alt="img"
        ></img>
      </div>
      <div>
        <span>임의</span>
        <span>profile</span>
      </div>
      <StyledButton onClick={() => handleCurChat(freind)}>chat</StyledButton>
    </StyledFriend>
  );
};

export default ChatRoom;
