import styled from "styled-components";
import { TiArrowSortedDown } from "@react-icons/all-files/ti/TiArrowSortedDown";
import { TiArrowSortedUp } from "@react-icons/all-files/ti/TiArrowSortedUp";
import { useState } from "react";

const StyledContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  border-bottom: solid 1px #dbdbdb;
`;
const StyledMyPlantsFolder = styled.div`
  p {
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
  }
`;

const MyPlants = () => {
  const [isFolderOpened, setIsFolderOpened] = useState(false);

  const handleFolderClick = () => {
    setIsFolderOpened(!isFolderOpened);
  };

  return (
    <StyledContainer>
      <StyledMyPlantsFolder onClick={handleFolderClick}>
        {isFolderOpened ? (
          <>
            <p>
              My Plants 펼치기
              <TiArrowSortedDown />
            </p>
          </>
        ) : (
          <p>
            My Plants 접기
            <TiArrowSortedUp />
          </p>
        )}
      </StyledMyPlantsFolder>
    </StyledContainer>
  );
};

export default MyPlants;
