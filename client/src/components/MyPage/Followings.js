import styled from "styled-components";
import { useEffect } from "react";
import FollowingsItem from "./FollowingsItem";

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

const Followings = ({handleFollowings, followings, handleChange, isOwnPage }) => {
  useEffect(() => {
      document.getElementById("bg").addEventListener("click", () => {
        handleFollowings();
      });
    }, [handleFollowings]);

  return (
      <StyledContainer>
        <p>팔로잉 목록</p>
        <ul>
          {followings.length > 0 
            ? followings.map((following, idx) => (
              <FollowingsItem isOwnPage={isOwnPage} handleFollowings={handleFollowings} following={following} key={following.followingId} handleChange={handleChange}/>
          )) : "팔로우하는 사람이 없습니다."}
        </ul>
      </StyledContainer>
  )
}

export default Followings