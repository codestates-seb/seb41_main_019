import styled from "styled-components";
import { useEffect, useState } from "react";
import axios from "axios";

import { TiArrowSortedDown } from "react-icons/ti";
import { TiArrowSortedUp } from "react-icons/ti";
import { BsGrid3X3 } from "react-icons/bs";
import { BsBookmark } from "react-icons/bs";

import Cookie from "../util/Cookie";
import MyPlants from "../components/MyPage/MyPlants";
import UserInfo from "../components/MyPage/UserInfo";
import Gallery from "../components/MyPage/Gallery";
import AddPlant from "../components/MyPage/AddPlant";
import View from "../components/Home/View";

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
  const cookie = new Cookie();
  const jwt = cookie.get("authorization")
  const memberId = Number(cookie.get("memberId"));

  const [userInfo, setUserInfo] = useState([]);
  const [postCount, setPostCount] = useState();
  const [isFolderOpened, setIsFolderOpened] = useState(false); // myPlants 펼치기/접기 상태
  const [galleryData, setGalleryData] = useState([]); // Gallery.js로 props 주는 데이터
  const [currentView, setCurrentView] = useState("postings"); // 현재 view(리스트)의 상태
  const [isAddPlantOpened, setIsAddPlantOpened] = useState(false);
  const [isViewOpened, setIsViewOpened] = useState(false);
  const [curPost, setCurPost] = useState();
  
  useEffect(() => {
    getUserInfo()
    getMyPostings()
  }, [])
  
  const getUserInfo = () => {
    try {
      axios({
        method: "get",
        url: `http://13.124.33.113:8080/members/${memberId}`,
        headers: {
          Authorization: jwt,
        },
      }).then((res) => {
        setUserInfo(res.data.data)
      })
    } catch (err) {
      console.error(err);
    }
  };

  const getMyPostings = () => {
    try {
      axios({
        method: "get",
        url: `http://13.124.33.113:8080/posts/members/${memberId}?page=1&size=20`,
        headers: {
          Authorization: jwt,
        },
      }).then((res) => {
        setPostCount(res.data.pageInfo.totalElements);
        setGalleryData(res.data.data)
      });
    } catch (err) {
      console.error(err)
    }
  };

  const getGalleryData = (url, view) => {
    try {
      axios({
        method: "get",
        url: url,
        headers: {
          Authorization: jwt,
        },
      }).then((res) => {
        setCurrentView(view)
        setGalleryData(res.data)
      })
    } catch (err) {
        console.error(err);
      }
  };

  const handleModal = (modal, postingId) => {
    if(modal === "AddPlant") {
      setIsAddPlantOpened(!isAddPlantOpened);
    } else if(postingId) {
      setCurPost(galleryData.filter((el) => 
        el.postingId === postingId
      )[0]) ;
      setIsViewOpened(!isViewOpened);
    } else {
      setIsViewOpened(!isViewOpened);
    }
    handleIsCovered();
  };

  const handlePostingsClick = () => {
    // 게시물 리스트 조회
    getGalleryData(`http://localhost:8080/posts/members/${memberId}?page=1&size=10`, "postings"); // 임시 제이슨 서버
  };

  const handleScrapsClick = () => {
    // 스크랩 조회
    alert("구현 예정")
    // getGalleryData("http://localhost:4000/data", "scraps");
  };

  const handlePlantClick = (plantId) => {
    // 반려식물 클릭시 해당건 조회
    setCurrentView("plant");
    // setGalleryData(myPlantsData[plantId].plantImgs);
  };

  const handleFolderClick = () => {
    setIsFolderOpened(!isFolderOpened);
  };

  return (
    <>
      {isCovered && isViewOpened && <View handleModal={handleModal} curPost={curPost}/>}
      {isCovered && isAddPlantOpened && <AddPlant jwt={jwt}  handleModal={handleModal} userInfo={userInfo} />}
      <StyledContainer>
        <UserInfo userInfo={userInfo} postCount={postCount}/>
        {isFolderOpened ? (
          <div className="container">
            <MyPlants
              userInfo={userInfo}
              handlePlantClick={handlePlantClick}
              handleModal={handleModal}
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
              <Gallery galleryData={galleryData} currentView={currentView} handleModal={handleModal}/>
            </StyledMyPlantFolder>
          </div>
        )}
      </StyledContainer>
    </>
  );
};

export default MyPage;
