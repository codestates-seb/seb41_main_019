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

  // 데이터 상태관리
  const [userInfo, setUserInfo] = useState([]); // 유저정보 (UserInfo.js로 props)
  const [postCount, setPostCount] = useState(); // 게시물 숫자 (setState는 UserInfo.js에서 핸들링)
  const [galleryData, setGalleryData] = useState([]); // 관심사 분리를 위하여 Gallery.js로 이관됨. handleModal 변경 후 삭제 예정
  const [curPost, setCurPost] = useState(); // View.js에 prop하기 위한 데이터
  const [postId, setPostId] = useState(null);
  const [commentId, setCommentId] = useState(null);
  const [commentMenu, setCommentMenu] = useState(false);
  
  // 모달 등 상태관리
  const [isFolderOpened, setIsFolderOpened] = useState(false); // myPlants 펼치기/접기 상태
  const [currentView, setCurrentView] = useState("postings"); // 현재 view (Gallery.js를 조건부 렌더링하기 위한 상태)
  const [isAddPlantOpened, setIsAddPlantOpened] = useState(false); // AddPlant.js 모달 조건부 렌더링하기 위한 상태
  const [isViewOpened, setIsViewOpened] = useState(false); // Gallery.js에서 map 함수의 요소 클릭 했을 때 모달(View.js) 렌더링 

  useEffect(() => {
    getUserInfo()
  }, [])
  
  const getUserInfo = () => {
      axios({
        method: "get",
        url: `http://13.124.33.113:8080/members/${memberId}`,
        headers: {
          Authorization: jwt,
        },
      }).then((res) => {
        setUserInfo(res.data.data)
      }).catch ((err) => 
      console.error(err))
  };

  const handleModal = (modal, postingData) => {
    if(modal === "AddPlant") {
      // setIsAddPlantOpened(!isAddPlantOpened);
    } else if(modal === "postings") {
      setCurPost(postingData);
    } else if(modal === "scraps") {
      setCurPost(postingData)
    }
    setIsViewOpened(!isViewOpened);
    handleIsCovered();
  };

  const handlePlantClick = (plantId) => {
    // 반려식물 클릭시 해당건 조회
    setCurrentView("plant");
  };

  const handleFolderClick = () => {
    setIsFolderOpened(!isFolderOpened);
  };

  const handleCommentMenu = () => {
    setCommentMenu(!commentMenu);
  }

  return (
    <>
      {isCovered && isViewOpened && <View handleModal={handleModal} curPost={curPost} handleCommentMenu={handleCommentMenu} setCommentId={setCommentId}/>}
      {isCovered && isAddPlantOpened && <AddPlant jwt={jwt} setGalleryData={setGalleryData} handleModal={handleModal} userInfo={userInfo} />}
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
              <Gallery setPostCount={setPostCount} currentView={currentView} userInfo={userInfo}  />
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
                  onClick={() => setCurrentView("postings")}
                  className={currentView === "postings" ? "selected" : ""}
                >
                  <BsGrid3X3 />
                  <span>게시물</span>
                </StyledChangeViewButton>
                <StyledChangeViewButton
                  onClick={() => setCurrentView("scraps")}
                  className={currentView === "scraps" ? "selected" : ""}
                >
                  <BsBookmark />
                  <span>스크랩</span>
                </StyledChangeViewButton>
              </StyledChangeViewContainer>
              <Gallery setPostCount={setPostCount} currentView={currentView} handleModal={handleModal} userInfo={userInfo}/>
            </StyledMyPlantFolder>
          </div>
        )}
      </StyledContainer>
    </>
  );
};

export default MyPage;
