import { useState, useEffect } from "react";
import styled from "styled-components";
import Input from "../Input";
import ChatRooms from "./ChatRooms";
import Chatting from "./Chatting";
import Friends from "./Friends";
import axios from "axios";
import Cookie from "../../../util/Cookie";

const StyledChat = styled.div`
  display: flex;
  flex-direction: column;
  width: 350px;
  height: 100%;
  padding: 21px 20px 0px 20px;
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

  > .chat-area::after {
      content: "";
      display: block;
      width: 400px;
      margin: 20px 0px 0px -20px;
      border-top: 1px solid #dbdbdb;
  }
`;

const Chat = ({ change }) => {
  const [ curChat, setCurChat ] = useState(null);
  const [ curFriend, setCurFriend ] = useState(null);
  const [ rooms, setRooms ] = useState([]);
  const [ friends, setFriends ] = useState([]);
  const [ chatChange, setChatChange] = useState(false);
  const cookie = new Cookie();

  useEffect(() => {
    axios({
      method: "get",
      url: `${process.env.REACT_APP_API}/chatroom/${cookie.get("memberId")}`,
      headers: { Authorization: cookie.get("authorization") }
    }).then(res => {
      setRooms(res.data);
      if(curChat) setCurChat(res.data.filter(data => data.chatRoomId === curChat.chatRoomId)[0]);
    })
  }, [change, chatChange])

  useEffect(() => {
    axios({
      method: "get",
      url: `${process.env.REACT_APP_API}/members/${cookie.get("memberId")}`,
      headers: { Authorization: cookie.get("authorization") }
    }).then(res => {
      setFriends(res.data.data.followingList);
    })
  }, [change, chatChange])
  
  return (  
    <>
      <StyledChat>
        {/* <Input label={"채팅"} /> */}
        {
          !curChat ? 
          <>
            <ChatRooms rooms={rooms} setCurChat={setCurChat} friends={friends} setCurFriend={setCurFriend} curChat={curChat}
              setChatChange={setChatChange} chatChange={chatChange} />
            <Friends setCurChat={setCurChat} friends={friends} rooms={rooms} setCurFriend={setCurFriend} 
              chatChange={chatChange} setChatChange={setChatChange} curChat={curChat} />
          </>
          : 
          <>
            <Chatting curChat={curChat} curFriend={curFriend} setCurChat={setCurChat} setCurFriend={setCurFriend}
              setChatChange={setChatChange} chatChange={chatChange} />
            <ChatRooms rooms={rooms} setCurChat={setCurChat} friends={friends} setCurFriend={setCurFriend} curChat={curChat} 
              setChatChange={setChatChange} chatChange={chatChange} />
            <Friends setCurChat={setCurChat} friends={friends} rooms={rooms} setCurFriend={setCurFriend} 
              chatChange={chatChange} setChatChange={setChatChange} curChat={curChat} />
          </>
        }
      </StyledChat>
    </>
  );
};

export default Chat;
