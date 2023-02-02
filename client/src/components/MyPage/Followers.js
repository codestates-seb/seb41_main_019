import styled from "styled-components";
import { useEffect } from "react";
import FollowerItem from "./FollwersItem";

const StyledContainer = styled.div`
  position: absolute;
  top:50%;
  left:50%;
  transform:translate(-50%, -50%);
  background-color: white;
  width: 400px;
  min-height : 500px;
  padding: 20px;
  z-index: 1000;

  ul  {
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

  > p {
    margin: 0px;
    font-size: 18px;
    font-weight: 600;
    margin: 0px 0px 20px 0px;
    padding-bottom: 20px;
    border-bottom: 1px solid #dbdbdb;
  }

`

const Followers = ({handleFollowers, followers, handleChange, isOwnPage }) => {
  useEffect(() => {
      document.getElementById("bg").addEventListener("click", () => {
        handleFollowers();
      });
    }, [handleFollowers]);

  return (
      <StyledContainer>
        <p>팔로워 목록</p>
        <ul>
          {followers.length > 0 
            ? followers.map((follower, idx) => (
              <FollowerItem handleChange={handleChange} isOwnPage={isOwnPage} handleFollowers={handleFollowers} follower={follower} key={follower.followerId}/>
          )) : "팔로워가 없습니다."}
        </ul>
      </StyledContainer>
  )
}

export default Followers