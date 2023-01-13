import styled from "styled-components";
import MyPlantSettings from "./MyPlantSettings";

import { BsTag, BsCalendar3 } from "react-icons/bs";
import { TbPlant } from "react-icons/tb";

const StyledContainer = styled.div`
  display: flex;
  width: 100%;
`;

const StyledPlantInfoBox = styled.div`
  width: 100%;
  div:first-child {
    display: flex;
    justify-content: center;
    > p {
      display: flex;
      font-size: 1.2em;
      align-items: center;
      > svg {
        margin: 0 5px;
        color: #2f4858;
        font-size: 1.3em;
      }
    }
  }
  div:nth-child(2) {
    display: flex;
    justify-content: space-evenly;
    > span {
      display: flex;
      align-items: center;
      > svg {
        margin: 0 5px;
        color: #2f4858;
        font-size: 1.1em;
      }
    }
  }
`;

const MyPlantInfo = ({ currentPlantData }) => {
  const { plantName, plantType } = currentPlantData;
  const adoptedAt = currentPlantData.adopted_at;
  return (
    <StyledContainer>
      <StyledPlantInfoBox>
        <div>
          <p>
            <TbPlant />
            {plantName}
          </p>
        </div>
        <div>
          <span>
            <BsTag />
            {plantType}
          </span>
          <span>
            <BsCalendar3 />
            {adoptedAt}
          </span>
        </div>
      </StyledPlantInfoBox>
      <MyPlantSettings />
    </StyledContainer>
  );
};

export default MyPlantInfo;
