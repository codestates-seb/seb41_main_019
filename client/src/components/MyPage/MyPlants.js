import styled from "styled-components";
import { useState } from "react";

import { GrPrevious } from "react-icons/gr";
import { GrNext } from "react-icons/gr";
import { RiPlantLine } from "react-icons/ri";
import { ReactComponent as Cookie } from "../../assets/svg/plus.svg";

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
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
  .image-wrapper {
    width: 100px;
    height: 100px;
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

const StyledMyPlantInfo = styled.div``;

const MyPlants = ({ myPlantsData }) => {
  const handleAddPlantClick = () => {
    // 식물 추가 로직
  };

  const handleMoveButtonClick = (go) => {
    // 이동 버튼 클릭
    alert(go);
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
          <StyledMyPlantsItem onClick={handleAddPlantClick}>
            <div className="image-wrapper">
              <Cookie />
            </div>
            <p>반려식물 추가</p>
          </StyledMyPlantsItem>
          {myPlantsData.length ? (
            myPlantsData.map((el) => {
              return (
                <StyledMyPlantsItem key={el.plantId}>
                  <div className="image-wrapper">
                    <img
                      className="image"
                      src={el.plantImgs[el.plantImgs.length - 1].imgUrl}
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
      <StyledMyPlantInfo></StyledMyPlantInfo>
    </StyledContainer>
  );
};

export default MyPlants;
