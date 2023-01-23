import styled from "styled-components";
import { IoChatbubbleEllipsesOutline } from "react-icons/io5"
import { useEffect, useState } from "react";
import axios from "axios";
import Cookie from "../../../util/Cookie";

const StyledFriend = styled.li`
  display: flex;
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

const ChatRoom = ({ room, setCurChat }) => {
  const [ receiver, setReceiver ] = useState([]);
  const cookie = new Cookie();  

  useEffect(() => {
    axios({
      method: "get",
      url: `http://13.124.33.113:8080/members/${room.receiverId}`,
      headers: { Authorization: cookie.get("authorization") }
    }).then(res => {
      const data = res.data.data;
      setReceiver({name: data.userName, text: data.profileText});
    })
  },[])

  return (
    <StyledFriend>
      <div>
        <img
          src="https://cdn.pixabay.com/photo/2020/05/17/20/21/cat-5183427__480.jpg"
          alt="img"
        ></img>
      </div>
      <div>
        <span>{receiver.name}</span>
        <span>{receiver.text}</span>
      </div>  
      <StyledButton onClick={() => {
        setCurChat(room);
      }}><IoChatbubbleEllipsesOutline /></StyledButton>
    </StyledFriend>
  );
};

export default ChatRoom;
