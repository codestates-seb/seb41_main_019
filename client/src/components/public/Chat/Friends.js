import styled from "styled-components";
import { friends } from "../../../assets/dummyData/friends";
import Friend from "./Friend";

const StyledFriends = styled.div`
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

const Friends = ({ handleCurChat }) => {
  return (
    <StyledFriends>
      <p>Follow List</p>
      {friends
        ? friends.map((friend, idx) => (
            <Friend friend={friend} key={idx} handleCurChat={handleCurChat} />
          ))
        : "친구가 없슴다."}
    </StyledFriends>
  );
};

export default Friends;
