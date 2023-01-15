import styled from "styled-components";
import { useState } from "react";
import axios from "axios";

import { TiArrowSortedDown } from "react-icons/ti";
import { TiArrowSortedUp } from "react-icons/ti";
import { BsGrid3X3 } from "react-icons/bs";
import { BsBookmark } from "react-icons/bs";

import MyPlants from "../components/MyPage/MyPlants";
import UserInfo from "../components/MyPage/UserInfo";
import Gallery from "../components/MyPage/Gallery";
import AddPlant from "../components/MyPage/AddPlant";

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin: 0 300px 0 270px;

  > .container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }
`;

const StyledMyPlantFolder = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 500px;
  border-bottom: solid 1px #dbdbdb;
  p {
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
  }
`;

const StyledChangeViewContainer = styled.div`
  display: flex;
  justify-content: space-around;
  border-bottom: solid 1px #dbdbdb;
`;

const StyledChangeViewButton = styled.div`
  display: flex;
  align-items: center;
  border: none;
  background-color: white;
  color: #ababa7;
  margin: 10px 0;
  cursor: pointer;
  span {
    margin: 0 5px;
  }
  &.selected {
    border-bottom: solid black 1px;
    color: black;
  }
`;

const MyPage = ({ isCovered, handleIsCovered }) => {
  const [isFolderOpened, setIsFolderOpened] = useState(false); // myPlants 펼치기/접기 상태
  const [galleryData, setGalleryData] = useState([]); // Gallery.js로 props 주는 데이터
  const [myPlantsData, setMyPlantsData] = useState([]); // My Plants 리스트 데이터
  const [currentView, setCurrentView] = useState(""); // 현재 view(리스트)의 상태

  const getGalleryData = async (url, view) => {
    try {
      setCurrentView(view);
      const response = await axios.get(url);
      setGalleryData(response.data);
      return response;
    } catch (err) {
      console.error(err);
    }
  };

  const getMyPlantsData = async () => {
    try {
      const response = await axios.get("http://localhost:4000/data");
      setMyPlantsData(response.data);
      return response;
    } catch (err) {
      console.error(err);
    }
  };

  const handlePostingsClick = () => {
    // 게시물 리스트 조회
    getGalleryData("http://localhost:4000/data", "postings"); // 임시 제이슨 서버
  };

  const handleScrapsClick = () => {
    // 스크랩 조회
    getGalleryData("http://localhost:4000/data", "scraps");
  };

  const handleMyPlantsActivate = () => {
    getMyPlantsData();
  };

  const handlePlantClick = (plantId) => {
    // 반려식물 클릭시 해당건 조회
    setCurrentView("plant");
    setGalleryData(myPlantsData[plantId].plantImgs);
  };

  const handleFolderClick = () => {
    setIsFolderOpened(!isFolderOpened);
    if (!isFolderOpened) {
      handleMyPlantsActivate();
    }
  };

  // MyPage 접속시 기본값으로 Postings 표시
  // defaultProps로 해결해야하나?

  return (
    <>
      {isCovered && <AddPlant handleIsCovered={handleIsCovered} />}
      <StyledContainer>
        <UserInfo />
        {isFolderOpened ? (
          <div className="container">
            <MyPlants
              myPlantsData={myPlantsData}
              handlePlantClick={handlePlantClick}
              handleIsCovered={handleIsCovered}
            />
            <StyledMyPlantFolder onClick={handleFolderClick}>
              <Gallery galleryData={galleryData} currentView={currentView} />
              <p>
                My Plants 접기 <TiArrowSortedUp />
              </p>
            </StyledMyPlantFolder>
          </div>
        ) : (
          <div className="container">
            <StyledMyPlantFolder>
              <p onClick={handleFolderClick}>
                My Plants 펼치기 <TiArrowSortedDown />
              </p>
              <StyledChangeViewContainer>
                <StyledChangeViewButton
                  onClick={handlePostingsClick}
                  className={currentView === "postings" ? "selected" : ""}
                >
                  <BsGrid3X3 />
                  <span>게시물</span>
                </StyledChangeViewButton>
                <StyledChangeViewButton
                  onClick={handleScrapsClick}
                  className={currentView === "scraps" ? "selected" : ""}
                >
                  <BsBookmark />
                  <span>스크랩</span>
                </StyledChangeViewButton>
              </StyledChangeViewContainer>
              <Gallery galleryData={galleryData} currentView={currentView} />
            </StyledMyPlantFolder>
          </div>
        )}
      </StyledContainer>
    </>
  );
};

export default MyPage;
