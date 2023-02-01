import styled from "styled-components";

import MyPlantInfo from "./MyPlantInfo";

import defaultIcon from "../../assets/img/plants/defaultPlantIcon.png"
import { GrPrevious } from "react-icons/gr";
import { GrNext } from "react-icons/gr";
import { RiPlantLine } from "react-icons/ri";
import { ReactComponent as Cookie } from "../../assets/svg/plus.svg";
import { useEffect, useState } from "react";
import axios from "axios";

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 500px;
  margin: 20px 0;
`;

const StyledMyPlantsDashBoard = styled.div`
  display: flex;
  width: 100%;
  .move-button {
    width: 25px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .icon {
    width: 100%;
    height: 30%;
  }
`;

const StyledListsContainer = styled.div`
  display: flex;
  width: 700px;
  align-items: center;
  overflow: hidden;
`;

const StyledMyPlantsItem = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 0 20px;
  .selected {
    border: 4px solid #ffb100;
  }
  .image-wrapper {
    width: 80px;
    height: 80px;
    border-radius: 70%;
    overflow: hidden;
    cursor: pointer;
  }
  .image {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  p {
    display: flex;
    justify-content: center;
    width: 100%;
    font-size: 0.8em;
  }
`;

const StyledNoContents = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 550px;
  svg {
    width: 50px;
    height: 50px;
    margin: 25px 0;
    color: #374435;
  }
  p {
    display: flex;
    justify-content: center;
    width: 100%;
    font-size: 0.8em;
  }
`;

const MyPlants = ({currentView, setCurrentView, userInfo, jwt, currentPlantData, setCurrentPlantData, handleAddPlant, handleChange }) => {
  const [myPlantsData, setMyPlantsData] = useState(null); // My Plants 리스트 데이터
  const [isPanelOpened, setIsPanelOpened] = useState(false);

  const getMyPlantsData = () => {
    axios({
      method: "get",
      url: `${process.env.REACT_APP_API}/${userInfo.memberId}/myplants?page=1&size=10`,
      headers: {
        "Authorization" : jwt
      },
    })
    .then((res) => {
      setMyPlantsData(res.data.data)}
    ).catch((err) => console.error(err))
  };
  
  useEffect(() => getMyPlantsData(), [handleChange])

  const handlePlantClick = (el) => {
    setCurrentView("plant")
    setCurrentPlantData(el);
    setIsPanelOpened(true);
  }

  const havePlantDeleted = () => {
    setIsPanelOpened(false);
    setCurrentPlantData(null);
  }

  const handleMoveButtonClick = (go) => {
    // 이동 버튼 클릭
    alert("구현 예정");
  };

  return (
    <StyledContainer>
      <StyledMyPlantsDashBoard>
        <div
          className="move-button"
          onClick={() => {
            handleMoveButtonClick("prev");
          }}
        >
          <GrPrevious className="icon" />
        </div>
        <StyledListsContainer>
          <StyledMyPlantsItem onClick={handleAddPlant}>
            <div className="image-wrapper">
              <Cookie className="image" />
            </div>
            <p>반려식물 추가</p>
          </StyledMyPlantsItem>
          {myPlantsData ? (
            myPlantsData.map((el) => {
              return (
                <StyledMyPlantsItem
                  key={el.myPlantsId}
                  onClick={() => handlePlantClick(el)}
                >
                  <div
                    className={
                      currentPlantData &&
                      currentPlantData.myPlantsId === el.myPlantsId
                        ? "selected image-wrapper"
                        : "image-wrapper"
                    }
                  >
                    <img
                      className="image"
                      src={el.length ? el.galleryList[el.galleryList.length - 1] : defaultIcon}
                      alt="each item"
                    />
                  </div>
                  <p>{el.plantName}</p>
                </StyledMyPlantsItem>
              );
            })
          ) : (
            <StyledNoContents>
              <div>
                <RiPlantLine />
              </div>
              <p>등록된 반려식물이 없습니다. 반려식물을 추가하세요.</p>
            </StyledNoContents>
          )}
        </StyledListsContainer>
        <div
          className="move-button"
          onClick={() => {
            handleMoveButtonClick("next");
          }}
        >
          <GrNext className="icon" />
        </div>
      </StyledMyPlantsDashBoard>
      {isPanelOpened && <MyPlantInfo havePlantDeleted={havePlantDeleted} handleChange={handleChange} currentPlantData={currentPlantData} setCurrentPlantData={setCurrentPlantData} currentView={currentView} setCurrentView={setCurrentView}/>}
    </StyledContainer>
  );
};

export default MyPlants;
