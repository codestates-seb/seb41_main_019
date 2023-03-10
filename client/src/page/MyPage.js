import styled from "styled-components";
import { useEffect, useState } from "react";
import axios from "axios";
import { useLocation } from "react-router-dom";

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
import CommentModal from "../components/Home/CommentModal";
import PlantImageView from "../components/MyPage/PlantImageView";
import Followers from "../components/MyPage/Followers";
import Followings from "../components/MyPage/Followings";
import Footer from "../components/public/Footer";

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 500px;
  margin: 0px auto;

  > .container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  @media screen and (max-width: 770px) {
    position: relative;
    width: 360px;
    top: 60px;
  }
`;

const StyledMyPlantFolder = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
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

const MyPage = ({ isCovered, handleIsCovered, change, handleChange }) => {
  const cookie = new Cookie();
  const jwt = cookie.get("authorization")
  const myMemberId = cookie.get("memberId");
  const memberId = useLocation().state.id;
  const path = useLocation().pathname;

  // ????????? ????????????
  const [isOwnPage, setIsOwnPage] = useState(myMemberId === memberId)
  const [userInfo, setUserInfo] = useState([]); // ???????????? (UserInfo.js??? props)
  const [postCount, setPostCount] = useState(); // ????????? ?????? (setState??? UserInfo.js?????? ?????????)
  const [curPost, setCurPost] = useState(); // View.js??? prop?????? ?????? ?????????
  const [commentId, setCommentId] = useState(null);
  const [commentMenu, setCommentMenu] = useState(false);
  const [currentPlantData, setCurrentPlantData] = useState(null);
  const [plantImageViewData, setPlantImageViewData] = useState({
    imgs: [],
    imgIdx: null,
  });
  
  // ?????? ??? ????????????
  const [isFolderOpened, setIsFolderOpened] = useState(false); // myPlants ?????????/?????? ??????
  const [currentView, setCurrentView] = useState("postings"); // ?????? view (Gallery.js??? ????????? ??????????????? ?????? ??????)
  const [isAddPlantOpened, setIsAddPlantOpened] = useState(false); // AddPlant.js ?????? ????????? ??????????????? ?????? ??????
  const [isViewOpened, setIsViewOpened] = useState(false); // Gallery.js?????? map ????????? ?????? ?????? ?????? ??? ??????(View.js) ????????? 
  const [isPlantImageViewOpened, setIsPlantImageViewOpened] = useState(false);
  const [isFollowersOpened, setIsFollowersOpened] = useState(false);
  const [isFollowingsOpened, setIsFollowingsOpened] = useState(false);

  useEffect(() => {
    if(isViewOpened && curPost) {
      axios({
        method: "get",
        url: `${process.env.REACT_APP_API}/posts/${curPost.postingId}`,
        headers: { Authorization: cookie.get("authorization") }
        }).then(res => {
            setCurPost(res.data.data)
        }).catch(e => {
           console.log(e);
        });
    }
  }, [handleChange])

  useEffect(() => {
    if (path === "/mypage" || myMemberId === memberId) {
      setIsOwnPage(true)
    } else {
      setIsOwnPage(false)
    }
    getUserInfo()
  }, [memberId, change])
  
  const getUserInfo = () => {
    axios({
      method: "get",
      url: `${process.env.REACT_APP_API}/members/${memberId}`,
      headers: {
        Authorization: jwt,
      },
    }).then((res) => {
      setUserInfo(res.data.data)
    }).catch ((err) => 
    console.error(err))
  };

  const handleFollowers = () => {
    setIsFollowersOpened(!isFollowersOpened)
    handleIsCovered();
  }

  const handleFollowings = () => {
    setIsFollowingsOpened(!isFollowingsOpened)
    handleIsCovered();
  }

  const handleAddPlant = () => {
    setIsAddPlantOpened(!isAddPlantOpened);
    handleIsCovered();
  }
 
  const handleModal = (postingData) => {
    setCurPost(postingData);
    setIsViewOpened(!isViewOpened);
    handleIsCovered();
  };

  const handlePlantImageView = (galleryData, galleryIdx) => {
    setPlantImageViewData({
      galleryData,
      galleryIdx,
    })
    setIsPlantImageViewOpened(!isPlantImageViewOpened);
    handleIsCovered()
  }

  const handleFolderClick = () => {
    if (isFolderOpened) {
      setIsFolderOpened(false);
      setCurrentView("postings")
    } else {
      setIsFolderOpened(true);
      setCurrentView("plant")
    }
  };

  const handleCommentMenu = () => {
    setCommentMenu(!commentMenu);
  }

  return (
    <>
      {commentMenu && <CommentModal post={curPost} handleCommentMenu={handleCommentMenu} handleChange={handleChange} commentId={commentId}/>}
      {isCovered && isViewOpened && <View handleModal={handleModal} curPost={curPost} handleChange={handleChange} handleCommentMenu={handleCommentMenu} setCommentId={setCommentId}/>}
      {isCovered && isAddPlantOpened && <AddPlant jwt={jwt} handleAddPlant={handleAddPlant} userInfo={userInfo} handleChange={handleChange} />}
      {isCovered && isPlantImageViewOpened && <PlantImageView handlePlantImageView={handlePlantImageView} plantImageViewData={plantImageViewData} />}
      {isCovered && isFollowersOpened && <Followers isOwnPage={isOwnPage} handleFollowers={handleFollowers} followers={userInfo.followerList} handleChange={handleChange}/>}
      {isCovered && isFollowingsOpened && <Followings isOwnPage={isOwnPage} handleFollowings={handleFollowings} followings={userInfo.followingList} handleChange={handleChange}/>}
      <StyledContainer>
        <UserInfo isOwnPage={isOwnPage} handleFollows={handleFollowers} handleFollowings={handleFollowings} userInfo={userInfo} postCount={postCount} setCurrentView={setCurrentView} handleChange={handleChange}/>
        {isFolderOpened && 
          <div className="container">
            <MyPlants
              isOwnPage={isOwnPage}
              currentPlantData={currentPlantData}
              setCurrentPlantData={setCurrentPlantData}
              userInfo={userInfo}
              currentView={currentView}
              setCurrentView={setCurrentView}
              handleAddPlant={handleAddPlant}
              handleChange={handleChange}
            />
            <StyledMyPlantFolder onClick={handleFolderClick}>
              {currentPlantData && <Gallery isCovered={isCovered} isPlantImageViewOpened={isPlantImageViewOpened} setPostCount={setPostCount} currentView={currentView} userInfo={userInfo} currentPlantData={currentPlantData} handlePlantImageView={handlePlantImageView}/> }
              {isOwnPage &&          
                <p>
                  My Plants ?????? <TiArrowSortedUp />
                </p>}
              {!isOwnPage && 
                <p>
                  {userInfo.userName}?????? Plants ?????? <TiArrowSortedUp />
                </p>}
            </StyledMyPlantFolder>
          </div>
        }
        {!isFolderOpened &&
          <div className="container">
          <StyledMyPlantFolder>
            {isOwnPage &&          
              <p onClick={handleFolderClick}>
                My Plants ?????? <TiArrowSortedDown />
              </p>}
            {!isOwnPage && 
              <p onClick={handleFolderClick}>
                {userInfo.userName}?????? Plants ?????? <TiArrowSortedDown />
              </p>}
            <StyledChangeViewContainer>
              <StyledChangeViewButton
                onClick={() => setCurrentView("postings")}
                className={currentView === "postings" && "selected"}
              >
                <BsGrid3X3 />
                <span>?????????</span>
              </StyledChangeViewButton>
              <StyledChangeViewButton
                onClick={() => setCurrentView("scraps")}
                className={currentView === "scraps" && "selected"}
              >
                <BsBookmark />
                <span>?????????</span>
              </StyledChangeViewButton>
            </StyledChangeViewContainer>
            <Gallery memberId={memberId} setPostCount={setPostCount} currentView={currentView} handleModal={handleModal} userInfo={userInfo}/>
          </StyledMyPlantFolder>
        </div>
        }
      </StyledContainer>
      <Footer />
    </>
  );
};

export default MyPage;
