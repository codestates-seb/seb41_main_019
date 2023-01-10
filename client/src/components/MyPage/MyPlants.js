import styled from "styled-components";
import axios from "axios";

import { GrPrevious } from "@react-icons/all-files/gr/GrPrevious";
import { GrNext } from "@react-icons/all-files/gr/GrNext";
import { ReactComponent as Cookie } from "../../assets/svg/plus.svg";
import { useState } from "react";

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
`;

const StyledMyPlantsItem = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 0 25px;
  .image-wrapper {
    width: 100px;
    height: 100px;
    border-radius: 70%;
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

const StyledMyPlantInfo = styled.div``;

const MyPlants = () => {
  const [myPlantsData, setMyPlantsData] = useState([]);

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
                <StyledMyPlantsItem>
                  {/* <div className="image-wrapper" key={el.postingId}>
                    <img className="image" src={el.imgUrl} alt="each item" />
                    <p>식물이름</p>
                  </div> */}
                </StyledMyPlantsItem>
              );
            })
          ) : (
            <div>식물을 등록하세요</div>
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
