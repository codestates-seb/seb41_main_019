import styled from "styled-components";
import { useState } from "react";

import { TiArrowSortedDown } from "@react-icons/all-files/ti/TiArrowSortedDown";
import { TiArrowSortedUp } from "@react-icons/all-files/ti/TiArrowSortedUp";

import MyPlants from "../components/MyPage/MyPlants";
import UserInfo from "../components/MyPage/UserInfo";

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 750px;
`;

const StyledMyPlantFolder = styled.div`
  border-bottom: solid 1px #dbdbdb;

  p {
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
  }
`;

const MyPage = () => {
  const [isFolderOpened, setIsFolderOpened] = useState(false);

  const handleFolderClick = () => {
    setIsFolderOpened(!isFolderOpened);
  };

  return (
    <StyledContainer>
      <UserInfo />
      {isFolderOpened ? (
        <>
          <MyPlants />
          <StyledMyPlantFolder onClick={handleFolderClick}>
            <p>
              My Plants 접기 <TiArrowSortedUp />
            </p>
          </StyledMyPlantFolder>
        </>
      ) : (
        <>
          <StyledMyPlantFolder onClick={handleFolderClick}>
            <p>
              My Plants 펼치기 <TiArrowSortedDown />
            </p>
          </StyledMyPlantFolder>
        </>
      )}
    </StyledContainer>
  );
};

export default MyPage;
