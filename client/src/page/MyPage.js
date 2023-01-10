import styled from "styled-components";
import { useEffect, useState } from "react";
import axios from "axios";

import { TiArrowSortedDown } from "@react-icons/all-files/ti/TiArrowSortedDown";
import { TiArrowSortedUp } from "@react-icons/all-files/ti/TiArrowSortedUp";
import { BsGrid3X3 } from "@react-icons/all-files/bs/BsGrid3X3";
import { BsBookmark } from "@react-icons/all-files/bs/BsBookmark";

import MyPlants from "../components/MyPage/MyPlants";
import UserInfo from "../components/MyPage/UserInfo";
import Gallery from "../components/MyPage/Gallery";

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
  .selected {
    border-top: solid black 1px;
    color: black;
  }
`;

const MyPage = () => {
  const [isFolderOpened, setIsFolderOpened] = useState(false);
  const [galleryData, setGalleryData] = useState([]);

  const handleFolderClick = () => {
    setIsFolderOpened(!isFolderOpened);
  };

  const getGalleryData = async (url) => {
    try {
      const response = await axios.get(url);
      setGalleryData(response.data);
      return response;
    } catch (err) {
      console.error(err);
    }
  };

  const handlePostingsClick = () => {
    // 게시물 리스트 조회
    getGalleryData("http://localhost:4000/data"); // 임시 제이슨 서버
  };

  // MyPage 접속시 기본값으로 Postings 표시
  handlePostingsClick();

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
      <StyledChangeViewContainer>
        <StyledChangeViewButton onClick={handlePostingsClick}>
          <BsGrid3X3 />
          <span>게시물</span>
        </StyledChangeViewButton>
        <StyledChangeViewButton>
          <BsBookmark />
          <span>스크랩</span>
        </StyledChangeViewButton>
      </StyledChangeViewContainer>
      <Gallery galleryData={galleryData} />
    </StyledContainer>
  );
};

export default MyPage;
