import styled from "styled-components";
import { useEffect, useState } from "react";
import axios from "axios";

import { TiArrowSortedDown } from "react-icons/ti";
import { TiArrowSortedUp } from "react-icons/ti";
import { BsGrid3X3 } from "react-icons/bs";
import { BsBookmark } from "react-icons/bs";

import MyPlants from "../components/MyPage/MyPlants";
import UserInfo from "../components/MyPage/UserInfo";
import Gallery from "../components/MyPage/Gallery";

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin: 0 auto;
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

const MyPage = () => {
  const [isFolderOpened, setIsFolderOpened] = useState(false); // myPlants 펼치기/접기 상태
  const [galleryData, setGalleryData] = useState([]); // Gallery.js로 props 주는 데이터
  const [myPlantsData, setMyPlantsData] = useState([]); // My Plants 리스트 데이터
  const [currentView, setCurrentView] = useState(""); // 현재 view(리스트)의 상태

  useEffect(() => {
    if (isFolderOpened) {
      handleMyPlantsActivate();
    }
  }, [isFolderOpened]);

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

  const handlePlantClick = () => {
    // 반려식물 클릭시 해당건 조회
  };

  const handleFolderClick = () => {
    setIsFolderOpened(!isFolderOpened);
  };

  // MyPage 접속시 기본값으로 Postings 표시
  // defaultProps로 해결해야하나?

  return (
    <StyledContainer>
      <UserInfo />
      {isFolderOpened ? (
        <>
          <MyPlants myPlantsData={myPlantsData} />
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
      <Gallery galleryData={galleryData} />
    </StyledContainer>
  );
};

export default MyPage;
