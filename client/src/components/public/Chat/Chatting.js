import styled from "styled-components";
import Massage from "./Massage";
import Friend from "./Friend";
import { useEffect, useRef, useState } from "react";
import { connect, subscribe, disConnect, send } from "../../../util/chat";
import Cookie from "../../../util/Cookie";
import { useChat } from "./useChat";
import { MdSend } from "react-icons/md"
import { soltChat } from "../../../util/soltChat";
import axios from "axios";

const StyledChatLog = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 400px;
  margin: 0px 0px 20px 0px;
  padding: 0px;
  overflow: scroll;
  ::-webkit-scrollbar {
    display: none;
  }
  text-align: center;
`;

const StyledChatting = styled.ul`
  display: flex;
  flex-direction: column;
  margin: 0px;
  padding: 0px;
  list-style: none;

  .receiver {
    align-items: flex-end;

    .msg {
      background-color: #d9d9d9;
      text-align: right;
    }
  }

  .sender {
    align-items: flex-start;

    .msg {
      color: white;
      background-color: #5e8b7e;
      text-align: left;
    }
  }
`;

const StyledDate = styled.p`
  font-size: 13px;
  font-weight: 300;
  display: inline-block;
  margin: 0px 0px 25px 0px;
  padding: 5px 10px 5px 10px;
  border-radius: 25px;
  background-color: #cdcbd6;
`;

const StyledInput = styled.div`
  display: flex;
  height: 30px;
  justify-content: space-between;

  input {
    width: 88%;
    border: 0px;
    background-color: #dbdbdb;
    border-radius: 10px;
    padding: 0px 0px 0px 10px;
    outline: none;
  }

  input:focus {
    box-shadow: 0 0 6px #5e8b7e;
  }

  button {
    margin: 3px 0px 0px 0px;
    padding: 0px;
    border: 0px;
    font-size: 22px;
    cursor: pointer;
    color: #808080;

    :hover {
      color: black;
    }
  }
`;

const Chatting = ({ curChat, curFriend, setCurChat, setCurFriend, setChatChange, chatChange }) => {
  const [ message, setMessage ] = useState("");
  const [ log, setLog ] = useChat(curChat);
  const [ res, setRes ] = useState(null);
  const input = useRef(null);
  const ul = useRef(null);
  const cookie = new Cookie();

  useEffect(() => {
    connect(curChat);

    setTimeout(() => {
      if(ul.current) ul.current.scrollIntoView({block: "end", behavior: "smooth"});
      subscribe(curChat, setRes);
    }, 150)
    input.current.focus();

    return () => {
      disConnect();
    }
  }, [])

  useEffect(() => {
    if(res) {
      const preLog = log.length > 0 ? log.reduce((acc, cur) => [...acc, ...cur]) : log;
      preLog.push(res)
      setLog(soltChat(preLog));
      setTimeout(() => {
        ul.current.scrollIntoView({block: "end", behavior: "smooth"});
      }, 150)
    }
  }, [res])

  const handleSend = () => {
    send(curChat, Number(cookie.get("memberId")), message);
    setMessage("");
    setTimeout(() => {
      ul.current.scrollIntoView({block: "end", behavior: "smooth"});
    }, 150)

    if(curChat.leaveId === Number(cookie.get("memberId"))) {
      axios({
        method: "patch",
        url: `${process.env.REACT_APP_API}/chatroom/${curChat.chatRoomId}`,
        headers: { "content-type": "Application/json", Authorization: cookie.get("authorization") },
        data: JSON.stringify({
          "memberId": cookie.get("memberId")
        })
      }).then(res => {
        setChatChange(!chatChange);
      }).catch(e => {
        console.log(e);
      })
    }

    input.current.focus();
  }

  return (
    <div className="chat-area">
      <div>
        <Friend friend={curFriend} setCurChat={setCurChat} setCurFriend={setCurFriend} top />
      </div>
      <StyledChatLog>
        { log.length > 0 ?
          log.map((dayMsg, idx) => {
            return (
              <div key={idx}>
                <StyledDate>{dayMsg[0].createdAt.slice(0, -9)}</StyledDate>
                <StyledChatting ref={ul}>
                  {dayMsg.map((msg, idx) => (
                    <Massage msg={msg} key={idx} user={cookie.get("memberId")} />
                  ))}
                </StyledChatting>
              </div>
            );
          }) : null
        }
      </StyledChatLog>
      <StyledInput>
        <input type="text" placeholder="text.." value={message} ref={input}
          onChange={(e) => setMessage(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && handleSend()}
        ></input>
        <button onClick={handleSend}><MdSend /></button>
      </StyledInput>
    </div>
  );
};

export default Chatting;
