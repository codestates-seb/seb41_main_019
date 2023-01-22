import styled from "styled-components";
import { IoChatbubbleEllipsesOutline } from "react-icons/io5"
import axios from "axios";
import Cookie from "../../../util/Cookie";
import { Profiler } from "react";
import { RiContactsBookLine } from "react-icons/ri";

const StyledFriend = styled.li`
  display: flex;
  ${({ top }) => (top ? "border-bottom: 1px solid #dbdbdb;" : null)}
  padding: 0px 0px 5px 0px;
  margin: 0px 0px 10px 0px;

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

const StyledButton = styled.button`
  border: 0px;
  cursor: pointer;
  background-color: white;
  
  svg {
    font-size: 22px;
  }
`;

const Friend = ({ friend, top }) => {
  const cookie = new Cookie();

  const createRoom = () => {
    axios({
      method: "post",
      url: `http://13.124.33.113:8080/chatroom`,
      headers: { Authorization: cookie.get("a uthorization") },
      data: {
        receiverId: friend.followingId,
        senderId: Number(cookie.get("memberId"))
      }
    }).then((res) => {
      console.log(res);
    }).catch(e => {
      console.log(e);
    })
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
        <span>{friend.profileText}</span>
      </div>
      {top ? (
        <StyledButton>x</StyledButton>
      ) : (
        <StyledButton onClick={() => {
          createRoom()
        }}><IoChatbubbleEllipsesOutline /></StyledButton>
      )}
    </StyledFriend>
  );
};

export default Friend;
