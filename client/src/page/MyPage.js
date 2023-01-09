import styled from "styled-components";
import { useState } from "react";

import { TiArrowSortedDown } from "@react-icons/all-files/ti/TiArrowSortedDown";
import { TiArrowSortedUp } from "@react-icons/all-files/ti/TiArrowSortedUp";
import { BsGrid3X3 } from "@react-icons/all-files/bs/BsGrid3X3";
import { BsBookmark } from "@react-icons/all-files/bs/BsBookmark";
import { FcAnswers } from "@react-icons/all-files/fc/FcAnswers";

import MyPlants from "../components/MyPage/MyPlants";
import UserInfo from "../components/MyPage/UserInfo";

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

const StyledMyPageGallery = styled.div`
  width: 100%;
  display: grid;
  grid-template-columns: repeat(3, 1fr);

  .image-wrapper {
    position: relative;
    width: 100%;
    border: solid 1px #dbdbdb;
  }
  .image-wrapper:after {
    display: block;
    content: "";
    padding-bottom: 100%;
  }

  .image {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
`;

const StyledNoContents = styled.div`
  width: 100%;
  height: 600px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #ababa7;
  svg {
    width: 100px;
    height: 100px;
  }
  p {
    font-size: 1.5em;
    color: white;
    cursor: default;
  }
`;

const MyPage = () => {
  const [isFolderOpened, setIsFolderOpened] = useState(false);

  const galleryItems = [
    {
      imgUrl:
        "https://plus.unsplash.com/premium_photo-1666299355271-b005dac85b6a?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1171&q=80",
    },
    {
      imgUrl:
        "https://images.unsplash.com/photo-1487798452839-c748a707a6b2?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=735&q=80",
    },
    {
      imgUrl:
        "https://plus.unsplash.com/premium_photo-1666299355271-b005dac85b6a?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1171&q=80",
    },
    {
      imgUrl:
        "https://images.unsplash.com/photo-1487798452839-c748a707a6b2?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=735&q=80",
    },
  ];

  const handleFolderClick = () => {
    setIsFolderOpened(!isFolderOpened);
  };

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
        <StyledChangeViewButton>
          <BsGrid3X3 />
          <span>게시물</span>
        </StyledChangeViewButton>
        <StyledChangeViewButton>
          <BsBookmark />
          <span>스크랩</span>
        </StyledChangeViewButton>
      </StyledChangeViewContainer>
      <StyledMyPageGallery>
        {galleryItems.length ? (
          galleryItems.map((el) => {
            return (
              <div className="image-wrapper">
                <a href="#">
                  <img className="image" src={el.imgUrl} alt="each item" />
                </a>
              </div>
            );
          })
        ) : (
          <StyledNoContents>
            <FcAnswers />
            <p>게시물이 없습니다</p>
          </StyledNoContents>
        )}
      </StyledMyPageGallery>
    </StyledContainer>
  );
};

export default MyPage;
