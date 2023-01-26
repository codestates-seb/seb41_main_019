import styled from "styled-components";
import Friend from "./Friend";

const StyledFriends = styled.div`
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
      margin: 0px;
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

const Friends = ({ setCurChat, friends, setCurFriend, rooms, setChatChange, chatChange, curChat }) => {
  return (
    <StyledFriends curChat={curChat}>
      <p>팔로우 목록</p>
      <ul>
        {friends.length > 0
          ? friends.map((friend, idx) => (
              <Friend friend={friend} key={idx} setCurChat={setCurChat} setCurFriend={setCurFriend} setChatChange={setChatChange}
                room={rooms.filter(room => room.receiverId === friend.followingId || room.senderId === friend.followingId)}
                chatChange={chatChange} />
            ))
          : "현재 팔로우 중인 친구가 없습니다."}
      </ul>
    </StyledFriends>
  );
};

export default Friends;
