import styled from "styled-components";
import MyPlantEdit from "./MyPlantEdit";
import { useState } from "react";

import { BsTag, BsCalendar3 } from "react-icons/bs";
import { TbPlant } from "react-icons/tb";

const StyledContainer = styled.div`
  position: relative;
  display: flex;
  width: 100%;
  border-top: 1px solid #dbdbdb;
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
      margin: 20px 0;
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

const StyledButtonsContainer = styled.div`
  display: flex;
  .wrapper {
    position: fixed;
    flex-direction: column;
    margin: 20px 0;
  }
`;
const StyledUpdateButtons = styled.div`
  width: 80px;
  height: 20px;
  background-color: white;
  cursor: pointer;
  border: 1px solid #dbdbdb;
  transform: translate(-80px, -10px);
  text-align: center;
`;

const MyPlantInfo = ({ havePlantDeleted, currentPlantData, setCurrentPlantData, handleChange, currentView, setCurrentView }) => {
  const [isUpdateMode, setIsUpdateMode] = useState(false);

  // const [form, setForm] = useState({
  //   plantName: plantName,
  //   plantType: plantType,
  //   plantBirthday: plantBirthDay,
  // })

  const handleUpdateMode = () => {
    setIsUpdateMode(!isUpdateMode);
  };

  const { plantName, plantType, plantBirthDay } = currentPlantData;
  return (
    <StyledContainer>
      {isUpdateMode ? (
        <StyledPlantInfoBox>
          <div>
            <p>
              <TbPlant />
              <input value={plantName} />
            </p>
          </div>
          <div>
            <span>
              <BsTag />
              <input value={plantType} />
            </span>
            <span>
              <BsCalendar3 />
              <input value={plantBirthDay} />
            </span>
          </div>
        </StyledPlantInfoBox>
      ) : (
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
              {plantBirthDay}
            </span>
          </div>
        </StyledPlantInfoBox>
      )}
      {isUpdateMode ? (
        <StyledButtonsContainer>
          <div className="wrapper">
            <StyledUpdateButtons>수정 완료</StyledUpdateButtons>
            <StyledUpdateButtons onClick={handleUpdateMode}>
              취소
            </StyledUpdateButtons>
          </div>
        </StyledButtonsContainer>
      ) : (
          <MyPlantEdit handleChange={handleChange} handleUpdateMode={handleUpdateMode} currentPlantData={currentPlantData} setCurrentPlantData={setCurrentPlantData} currentView={currentView} setCurrentView={setCurrentView} havePlantDeleted={havePlantDeleted}/>    
      )}
    </StyledContainer>
  );
};

export default MyPlantInfo;
