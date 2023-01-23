import styled from "styled-components";
import Massage from "./Massage";
import Friend from "./Friend";
import { useEffect, useState } from "react";
import { connect, subscribe, disConnect, send } from "../../../util/chat";
import { chatLogData } from "../../../assets/dummyData/chatLogData"
import axios from "axios";
import Cookie from "../../../util/Cookie";

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

  .send {
    text-align: left;
    span {
      background-color: #d9d9d9;
    }

    p {
      padding: 2px 0px 0px 3px;
    }
  }

  .to {
    text-align: right;

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

const Chatting = ({ curChat }) => {
  const [ message, setMessage ] = useState("");
  const [ curLog, setCurLog ] = useState(15);
  const [ temp, setTemp ] = useState(false);
  const cookie = new Cookie();

  useEffect(() => {
    axios({
      method: "get",
      url: `http://13.124.33.113:8080/message/${curChat.chatRoomId}?page=1&size=15`,
      headers: { Authorization: cookie.get("authorization") }
    }).then(res => {
      console.log(curChat.chatRoomId, res);
    }).catch(e => {
      console.log(e);
    })
  }, [curChat])

  const soltChat = () => {
    const solted = [];
    chatLogData[0]
      .filter((data, idx) => idx >= chatLogData.length - curLog)
      .reduce((acc, cur) => {
        if (!solted[0]) solted.push([acc]);

        if (solted[solted.length - 1][0].time !== cur.time) {
          solted.push([cur]);
        } else {
          solted[solted.length - 1].push(cur);
        }
      });

    return solted;
  };

  soltChat();

  console.log(curChat);

  const handleSend = () => {
    send(curChat.chatRoomId, curChat.receiverId, curChat.senderId, message);
  }

  return (
    <div className="chat-area">
      <button onClick={() => connect()}>ac</button>
      <button onClick={() => disConnect()}>de</button>
      <button onClick={() => subscribe(curChat.chatRoomId)}>sub</button>
      <button onClick={() => setTemp(!temp)}>send</button>
      <div>
        {/* <Friend top /> */}
      </div>
      <StyledChatLog>
        {soltChat().map((data, idx) => {
          return (
            <div key={idx}>
              <StyledDate>{data[0].time}</StyledDate>
              <StyledChatting>
                {data.map((data, idx) => (
                  <Massage data={data} key={idx} />
                ))}
              </StyledChatting>
            </div>
          );
        })}
      </StyledChatLog>
      <StyledInput>
        <input type="text" placeholder="text.." value={message} onChange={(e) => setMessage(e.target.value)}></input>
        <button onClick={handleSend}>Send</button>
      </StyledInput>
    </div>
  );
};

export default Chatting;
