import styled from "styled-components";
import ChatRoom from "./ChatRoom";

const StyledChatList = styled.div`
  max-height: 30%;

  ul {
    margin: 0px;
    padding: 0px;
    list-style: none;
    overflow: scroll;
    ::-webkit-scrollbar {
      display: none;
    }

    li: last-child {
      margin : 0px;
    }
  }

  > p::before {
    content: "";
    display: block;
    width: 400px;
    margin: 0px 0px 20px -20px;
    border-top: 1px solid #dbdbdb;
  }

  > p {
    margin: 0px;
    font-size: 18px;
    font-weight: 600;
    margin: 0px 0px 10px 0px;
  }
`;

const ChatRooms = ({ handleCurChat, chatLog, freinds }) => {
  return (
    <StyledChatList>
      <p>Chat List</p>
      <ul>
        {chatLog
          ? chatLog.map((data, idx) => {
              return (
                <ChatRoom
                  key={idx}
                  freind={freinds.filter((friend) => friend.id === data.to)}
                  handleCurChat={handleCurChat}
                />
              );
            })
          : "현재 대화중인 상대가 없습니다."}
      </ul>
    </StyledChatList>
  );
};

export default ChatRooms;
