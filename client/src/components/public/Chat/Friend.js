import styled from "styled-components";
import { AiOutlineUserDelete } from "react-icons/ai"
import { MdOutlineKeyboardArrowLeft } from "react-icons/md"
import axios from "axios";
import Cookie from "../../../util/Cookie";
import { follow } from "../../../util/follow";

const StyledFriend = styled.li`
  display: flex;
  padding: 3px 5px 2px 5px;
  margin: 0px 0px 10px 0px;
  align-items: center;
  cursor: ${({ top }) => !top ? "pointer" : null};

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
    ${({ top }) => !top ? "background-color: #DBDBDB;" : null}
    ${({ top }) => !top ? "border-radius: 3px;" : null}
  }

  :hover button {
    ${({ top }) => !top ? "background-color: #DBDBDB;" : null}
  }

  .top {
    margin-right: -20px;
  }
`;

const StyledButton = styled.button`
  border: 0px;
  cursor: pointer;
  background-color: white;
  
  svg {
    font-size: 22px;
    color: #808080;

    :hover {
      color: #D96846;
    }
  }
`;

const Friend = ({ friend, top, setCurChat, setCurFriend, room, setChatChange, chatChange }) => {
  const cookie = new Cookie();

  const createRoom = () => {
    if(!room.length > 0) {
      axios({
        method: "post",
        url: `${process.env.REACT_APP_API}/chatroom`,
        headers: { Authorization: cookie.get("authorization") },
        data: { 
          receiverId: friend.followingId,
          senderId: Number(cookie.get("memberId"))
        }
      }).then((res) => {
        setCurFriend(friend);
        setCurChat(res.data.data);
        setChatChange(!chatChange);
      }).catch(e => {
        console.log(e);
      })
    } else {
      setCurFriend(friend);
      setCurChat(...room);
    }
  }

  return (
    <StyledFriend top={top} onClick={(e) => {
        e.stopPropagation();
        if(!top) createRoom()
      }}>
      <div>
        <img
          src={friend.profileImage}
          alt="img"
        ></img>
      </div>
      <div>
        <span>{friend.userName}</span>
        {
          top ? null : <span>{friend.profileText}</span>
        }
      </div>
      {top ? (
        <StyledButton className="top" onClick={() => {
          setCurFriend(null);
          setCurChat(null);
        }}>
          <MdOutlineKeyboardArrowLeft />
        </StyledButton>
      ) : (
        <StyledButton onClick={(e) => {
          e.stopPropagation();
          follow(false, friend.followingId, setChatChange)
        }}>
          <AiOutlineUserDelete />
        </StyledButton>
      )}
    </StyledFriend>
  );
};

export default Friend;
