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

  > .chat-area::before {
    content: "";
    display: block;
    width: 400px;
    margin: 0px 0px 20px -20px;
    border-top: 1px solid #dbdbdb;
  }
`;

const Chat = ({ change }) => {
  const [ curChat, setCurChat ] = useState(null);
  const [ curFriend, setCurFriend ] = useState(null);
  const [ rooms, setRooms ] = useState([]);
  const [ friends, setFriends ] = useState([]);
  const cookie = new Cookie();

  useEffect(() => {
    axios({
      method: "get",
      url: `http://13.124.33.113:8080/chatroom/${cookie.get("memberId")}`,
      headers: { Authorization: cookie.get("authorization") }
    }).then(res => {
      setRooms(res.data);
    })
  }, [change])

  useEffect(() => {
    axios({
      method: "get",
      url: `http://13.124.33.113:8080/members/${cookie.get("memberId")}`,
      headers: { Authorization: cookie.get("authorization") }
    }).then(res => {
      // const following  = res.data.data.followingList;
      // const follower = res.data.data.followerList;

      // setFriends(following.filter((flwi) => {
      //   return follower.find(flwe => flwe.followerId === flwi.followingId);
      // }))

      setFriends(res.data.data.followingList);
    })
  }, [change])
  
  return (  
    <>
      <StyledChat>
        <Input label={"채팅"} />
        {
          !curChat ? 
          <>
            <ChatRooms rooms={rooms} setCurChat={setCurChat} friends={friends} setCurFriend={setCurFriend} />
            <Friends setCurChat={setCurChat} friends={friends} rooms={rooms} setCurFriend={setCurFriend} />
          </>
          : <Chatting curChat={curChat} curFriend={curFriend} setCurChat={setCurChat} setCurFriend={setCurFriend} />
        }
      </StyledChat>
    </>
  );
};

export default Chat;
