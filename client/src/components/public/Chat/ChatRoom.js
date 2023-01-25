import styled from "styled-components";
import { IoChatbubbleEllipsesOutline } from "react-icons/io5"

const StyledFriend = styled.li`
  display: flex;
  margin: 0px 0px 10px 0px;
  padding: 0px 0px 5px 0px;
  align-items: center;

  div:nth-of-type(1) {
    width: 10%;
    margin: 0px 10px 0px 0px;

    img {
      width: 24px;
      height: 24px;
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
      font-size: 12px;
      color: black;
    }
  }
`;

const StyledButton = styled.button`
  border: 0px;
  cursor: pointer;
  background-color: white;
  
  svg {
    font-size: 22px;
  }
`;

const ChatRoom = ({ room, setCurChat, friend, setCurFriend }) => {
  return (
    <>
    {
      // 탈퇴한 이용자에 대한 chatRoom 임시처리
      friend.length > 0 ?
        <StyledFriend>
          <div>
            <img
              src="https://cdn.pixabay.com/photo/2020/05/17/20/21/cat-5183427__480.jpg"
              alt="img"
            ></img>
          </div>
          <div>
            <span>{friend.length > 0 ? friend[0].userName : null}</span>
            <span>{friend.length > 0 ? friend[0].profileText : null}</span>
          </div>  
          <StyledButton onClick={() => {
            setCurFriend(...friend);
            setCurChat(room);
          }}><IoChatbubbleEllipsesOutline /></StyledButton>
        </StyledFriend> : null
    }
    </>
  );
};

export default ChatRoom;
