import axios from "axios";
import { useEffect } from "react";
import styled from "styled-components";
import Cookie from "../../../util/Cookie";
import Friend from "./Friend";
import { useState } from "react";

const StyledFriends = styled.div`
  max-height: 40%;

  ul {
    display: flex;
    flex-direction: column-reverse;
    margin: 0px;
    padding: 0px;
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

const Friends = ({ handleCurChat }) => {
  const [ freinds, setFriends ] = useState([]);

  useEffect(() => {
    axios({
      method: "get",
      url: `http://13.124.33.113:8080/members/  ${new Cookie().get("memberId")}`,
      headers: { Authorization: new Cookie().get("authorization") }
    }).then(res => {
      setFriends(res.data.data.followingList);
    })
  }, [])

  return (
    <StyledFriends>
      <p>팔로우 목록</p>
      <ul>
        {freinds
          ? freinds.map((friend, idx) => (
              <Friend friend={friend} key={idx} handleCurChat={handleCurChat} />
            ))
          : "현재 팔로우 중인 친구가 없습니다."}
      </ul>
    </StyledFriends>
  );
};

export default Friends;
