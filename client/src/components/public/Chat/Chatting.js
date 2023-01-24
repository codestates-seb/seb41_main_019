import styled from "styled-components";
import Massage from "./Massage";
import Friend from "./Friend";
import { useEffect, useState } from "react";
import { connect, subscribe, disConnect, send } from "../../../util/chat";
import Cookie from "../../../util/Cookie";
import { useChat } from "./useChat";

const StyledChatLog = styled.div`
  display: flex;
  flex-direction: column-reverse;
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
  margin: 0px;
  padding: 0px;
  list-style: none;

  .receiver {
    text-align: right;
    span {
      background-color: #d9d9d9;
    }

    p {
      padding: 2px 0px 0px 3px;
    }
  }

  .sender {
    text-align: left;

    span {
      color: white;
      background-color: #5e8b7e;
    }

    p {
      padding: 2px 3px 0px 0px;
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
    width: 80%;
    border: 0px;
    background-color: #dbdbdb;
    border-radius: 10px;
    padding: 0px 0px 0px 10px;
    outline: none;
  }

  input:focus {
    box-shadow: 0 0 6px #5e8b7e;
  }
`;

const Chatting = ({ curChat, curFriend, setCurChat, setCurFriend }) => {
  const [ message, setMessage ] = useState("");
  const [ log, setLog ] = useChat(curChat);
  const cookie = new Cookie();

  useEffect(() => {
    connect(curChat, log, setLog);

    return () => {
      disConnect();
    }
  }, [])

  const handleSend = () => {
    send(curChat, message);
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
                <StyledChatting>
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
        <input type="text" placeholder="text.." value={message} onChange={(e) => setMessage(e.target.value)}></input>
        <button onClick={handleSend}>Send</button>
      </StyledInput>
    </div>
  );
};

export default Chatting;
