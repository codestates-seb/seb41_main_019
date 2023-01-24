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

  > p {
    margin: 0px;
    font-size: 18px;
    font-weight: 600;
    margin: 0px 0px 10px 0px;
  }
`;

const ChatRooms = ({ rooms, setCurChat, friends, setCurFriend }) => {
  return (
    <StyledChatList>
      <p>채팅 목록</p>  
      <ul>
        {rooms.length > 0
          ? rooms.map((room, idx) => {
              return (
                <ChatRoom
                  friend={friends.filter(friend => friend.followingId === room.receiverId || friend.followingId === room.senderId)}
                  key={idx}
                  room={room}
                  setCurChat={setCurChat}
                  setCurFriend={setCurFriend}
                />
              );
            })
          : "현재 대화중인 상대가 없습니다."}
      </ul>
    </StyledChatList>
  );
};

export default ChatRooms;
