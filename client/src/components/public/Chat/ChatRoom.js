import styled from "styled-components"; 
import { MdDoDisturbOn } from "react-icons/md"
import { useEffect, useState } from "react";
import axios from "axios";
import Cookie from "../../../util/Cookie";

const StyledFriend = styled.li`
  display: flex;
  margin: 0px 0px 10px 0px;
  padding: 3px 5px 2px 5px;
  align-items: center;
  cursor: pointer;

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

  :hover {
    background-color: #DBDBDB;
    border-radius: 3px;
  }

  :hover button {
    background-color: #DBDBDB;
  }
`;

const StyledButton = styled.button`
  border: 0px;
  cursor: pointer;
  background-color: white;
  
  svg {
    color: #808080;
    font-size: 22px;

    :hover { 
      color: #D96846;
    }
  }
`;

const ChatRoom = ({ room, setCurChat, friend, setCurFriend, setChatChange, chatChange }) => {
  const cookie = new Cookie();

  const deleteChat = () => {
    //leaveId null 인 경우 추가
    if(!room.leaveId) {
      axios({
        method: "patch",
        url: `${process.env.REACT_APP_API}/chatroom/${room.chatRoomId}`,
        headers: { "content-type": "Application/json", Authorization: cookie.get("authorization") },
        data: JSON.stringify({
          "memberId": cookie.get("memberId")
        })
      }).then(res => {
        setChatChange(!chatChange);
      }).catch(e => {
        console.log(e);
      })
    }else if(room.leaveId !== Number(cookie.get("memberId"))) {
      //leaveId 확인, 상대방이 채팅방을 떠났을 경우 해당 챗룸 삭제
      axios({
        method: "delete",
        url: `${process.env.REACT_APP_API}/chatroom/${room.chatRoomId}`,
        headers: { "content-type": "Application/json", Authorization: cookie.get("authorization") },
      }).then(res => {
        setChatChange(!chatChange);
      }).catch(e => {
        console.log(e);
      })
    }
  }

  return (
    <>
    { friend ?
        <StyledFriend onClick={() => {
          setCurFriend(friend);
          setCurChat(room);
        }}
          className={room.leaveId === Number(cookie.get("memberId")) ? "deleted" : null}
        >
          <div>
            <img
              src={friend.profileImage}
              alt="img"
            ></img>
          </div>
          <div>
            <span>{friend.userName}</span>
            <span>{friend.profileText}</span>
          </div>  
          <StyledButton onClick={(e) => {
            e.stopPropagation();
            deleteChat();
          }}>
            <MdDoDisturbOn />
          </StyledButton>
        </StyledFriend> : null
    }
    </>
  );
};

export default ChatRoom;
