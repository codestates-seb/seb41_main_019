import styled from "styled-components";
import { chatLog } from "../../../assets/dummyData/chat";
import Massage from "./Massage";

const StyledButton = styled.button`
  width: 6%;
  margin: 0px 0px 0px auto;
`;

const StyledChatting = styled.ul`
  width: 100%;
  height: 400px;
  margin: 0px;
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
const Chatting = ({ setIsOpend }) => {
  return (
    <>
      <StyledButton onClick={() => setIsOpend(false)}>x</StyledButton>
      <StyledChatting>
        {chatLog.map((data, idx) => {
          return <Massage data={data} key={idx} />;
        })}
      </StyledChatting>
    </>
  );
};

export default Chatting;
