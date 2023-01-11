import styled from "styled-components";
import { chatLog } from "../../../assets/dummyData/chat";
import Massage from "./Massage";

const StyledButton = styled.button`
  width: 6%;
  float: right;
  margin: 0px 0px 10px 0px;
`;

const StyledChatting = styled.ul`
  width: 100%;
  height: 400px;
  margin: 0px 0px 20px 0px;
  padding: 0px;
  overflow: scroll;
  list-style: none;
  ::-webkit-scrollbar {
    display: none;
  }

  .send {
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
const Chatting = ({ setIsOpend }) => {
  return (
    <div className="chat-area">
      <StyledButton onClick={() => setIsOpend(false)}>x</StyledButton>
      <StyledChatting>
        {chatLog.map((data, idx) => {
          return <Massage data={data} key={idx} />;
        })}
      </StyledChatting>
      <StyledInput>
        <input type="text" placeholder="text.."></input>
        <button>Send</button>
      </StyledInput>
    </div>
  );
};

export default Chatting;
