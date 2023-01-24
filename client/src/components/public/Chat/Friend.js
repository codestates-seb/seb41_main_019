import styled from "styled-components";
import { IoChatbubbleEllipsesOutline } from "react-icons/io5"
import axios from "axios";
import Cookie from "../../../util/Cookie";
import { disConnect } from "../../../util/chat";

const StyledFriend = styled.li`
  display: flex;
  padding: 0px 0px 5px 0px;
  margin: 0px 0px 10px 0px;
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

const Friend = ({ friend, top, setCurChat, setCurFriend, room }) => {
  const cookie = new Cookie();

  const createRoom = () => {
    if(!room.length > 0) {
      axios({
        method: "post",
        url: `http://13.124.33.113:8080/chatroom`,
        headers: { Authorization: cookie.get("authorization") },
        data: { 
          receiverId: friend.followingId,
          senderId: Number(cookie.get("memberId"))
        }
      }).then((res) => {
        setCurFriend(friend);
        setCurChat(res.data.data);
      }).catch(e => {
        console.log(e);
      })
    } else {
      setCurFriend(friend);
      setCurChat(...room);
    }
  }

  return (
    <StyledFriend top={top}>
      <div>
        <img
          src="https://cdn.pixabay.com/photo/2020/05/17/20/21/cat-5183427__480.jpg"
          alt="img"
        ></img>
      </div>
      <div>
        <span>{friend.userName}</span>
        {
          top ? null : <span>{friend.profileText}</span>
        }
      </div>
      {top ? (
        <StyledButton onClick={() => {
          // disConnect();
          setCurFriend(null);
          setCurChat(null);
        }}>x</StyledButton>
      ) : (
        <StyledButton onClick={createRoom}><IoChatbubbleEllipsesOutline /></StyledButton>
      )}
    </StyledFriend>
  );
};

export default Friend;
