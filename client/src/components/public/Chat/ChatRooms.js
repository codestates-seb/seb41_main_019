import styled from "styled-components";
import ChatRoom from "./ChatRoom";

const StyledChatList = styled.div`
  ul {
    ${({curChat}) => curChat ? "max-height: 125px;" : null}
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

  > p {
    margin: 0px;
    font-size: 18px;
    font-weight: 600;
    margin: 0px 0px 10px 0px;
  }

  .deleted {
    display: none;
  }
`;

const ChatRooms = ({ rooms, setCurChat, friends, setCurFriend, curChat, setChatChange, chatChange }) => {
  return (
    <StyledChatList curChat={curChat}>
      <p>채팅 목록</p>  
      <ul>
        {rooms.length > 0
          ? rooms.map((room, idx) => {
              return (
                <ChatRoom
                  friend={friends.filter(friend => friend.followingId === room.receiverId || friend.followingId === room.senderId)[0]}
                  key={idx} 
                  room={room}
                  setCurChat={setCurChat}
                  setCurFriend={setCurFriend}
                  setChatChange={setChatChange}
                  chatChange={chatChange}
                />
              );
            })
          : "현재 대화중인 상대가 없습니다."}
      </ul>
    </StyledChatList>
  );
};

export default ChatRooms;
