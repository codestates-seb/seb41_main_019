import styled from "styled-components";
import MyPlantEdit from "./MyPlantEdit";
import { useState } from "react";
import Cookie from "../../util/Cookie";

import { BsTag, BsCalendar3 } from "react-icons/bs";
import { TbPlant } from "react-icons/tb";
import axios from "axios";
import { useEffect } from "react";

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

      @media screen and (max-width: 770px) {
        font-size: 16px;
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

      @media screen and (max-width: 770px) {
        font-size: 13px;
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

const MyPlantInfo = ({ isOwnPage, havePlantDeleted, currentPlantData, setCurrentPlantData, handleChange, currentView, setCurrentView }) => {
  const cookie = new Cookie();
  const jwt = cookie.get("authorization")

  const nowDate = new Date();
  const today = nowDate.toISOString().substring(0,10)
  
  const { plantName, plantType, plantBirthDay, myPlantsId} = currentPlantData;
  const [isUpdateMode, setIsUpdateMode] = useState(false);
  const [form, setForm] = useState({
    plantName: plantName,
    plantType: plantType,
    plantBirthday: plantBirthDay,
  })

  const handleSubmit = (e) => {
    e.preventDefault();
    axios({
      method: "patch",
      url: `${process.env.REACT_APP_API}/myplants/${myPlantsId}`,
      headers: {
        "Authorization" : jwt
      },
      data: {
        "myPlantsId" : myPlantsId,
        "plantName" : form.plantName,
        "plantType" : form.plantType,
        "plantBirthDay" : form.plantBirthday
      }
    }).then(res => {
      console.log(res.data)
      handleUpdateMode();
      alert("수정되었습니다.")
    }).catch(
      e => {
        console.error(e)
      }
    )
  }

  const handleInputChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleUpdateMode = () => {
    setIsUpdateMode(!isUpdateMode);
  };

  return (
    <StyledContainer>
      {isUpdateMode && 
        <>
          <StyledPlantInfoBox>
            <div>
              <p>
                <TbPlant />
                <input value={form.plantName} onChange={handleInputChange} type="text" name="plantName"/>
              </p>
            </div>
            <div>
              <span>
                <BsTag />
                <input value={form.plantType} onChange={handleInputChange} type="text" name="plantType"/>
              </span>
              <span>
                <BsCalendar3 />
                <input value={form.plantBirthday} onChange={handleInputChange} max={today} type="date" name="plantBirthday"/>
              </span>
            </div>
          </StyledPlantInfoBox>
          <StyledButtonsContainer>
            <div className="wrapper">
              <StyledUpdateButtons onClick={handleSubmit}>수정 완료</StyledUpdateButtons>
              <StyledUpdateButtons onClick={handleUpdateMode}>
                취소
              </StyledUpdateButtons>
            </div>
          </StyledButtonsContainer>
        </>
      }
      {!isUpdateMode &&
      <>
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
        {isOwnPage &&
          <MyPlantEdit handleChange={handleChange} handleUpdateMode={handleUpdateMode} currentPlantData={currentPlantData} setCurrentPlantData={setCurrentPlantData} currentView={currentView} setCurrentView={setCurrentView} havePlantDeleted={havePlantDeleted}/> 
        }
      </>   
      }
    </StyledContainer>
  );
};

export default MyPlantInfo;
