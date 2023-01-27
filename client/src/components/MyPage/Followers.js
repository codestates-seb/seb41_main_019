import styled from "styled-components";
import { useEffect, useState } from "react";

const StyledContainer = styled.div`
    position: absolute;
    top:50%;
    left:50%;
    transform:translate(-50%, -50%);
    background-color: white;
    width: 1240px;
    height: 900px;
    z-index: 1000;
`

const Followers = ({handleFollowers, followers}) => {
  useEffect(() => {
      document.getElementById("bg").addEventListener("click", () => {
        handleFollowers();
      });
    }, [handleFollowers]);

    console.log(followers)

  return (
      <StyledContainer>
        <p>팔로워 목록</p>
      </StyledContainer>
  )
}

export default Followers