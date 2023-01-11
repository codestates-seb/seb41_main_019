import styled from "styled-components";
import Friend from "./Friend";

const StyledFriends = styled.div`
  max-height: 40%;

  ul {
    display: flex;
    flex-direction: column-reverse;
    height: 85%;
    margin: 0px;
    padding: 0px;
    overflow: scroll;
    ::-webkit-scrollbar {
      display: none;
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

const Friends = ({ handleCurChat, freinds }) => {
  return (
    <StyledFriends>
      <p>Follow List</p>
      <ul>
        {freinds
          ? freinds.map((friend, idx) => (
              <Friend friend={friend} key={idx} handleCurChat={handleCurChat} />
            ))
          : "친구가 없슴다."}
      </ul>
    </StyledFriends>
  );
};

export default Friends;
