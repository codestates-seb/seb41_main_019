import styled from "styled-components";
import { useState, useEffect } from "react";
import axios from "axios";
import Cookie from "../../util/Cookie";

import { FcAnswers } from "react-icons/fc";

const StyledContainer = styled.div`
  position: relative;
  display: flex;
  justify-content: center;
`;

const StyledMyPageGallery = styled.div`
  width: 100%;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  cursor: pointer;

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

const Gallery = ({ currentView, handleModal, userInfo, setPostCount, currentPlantData }) => {
  const cookie = new Cookie();
  const jwt = cookie.get("authorization")
  const memberId = Number(cookie.get("memberId"));

  const [galleryData, setGalleryData] = useState([])

  useEffect(() => {
    setGalleryData([])
  } ,[currentView])
  
  const getGalleryData = (view) => {
    if(view === 'postings') {
      axios({
        method: "get",
        url: `http://13.124.33.113:8080/posts/members/${memberId}?page=1&size=10`,
        headers: {
          Authorization: jwt,
        },
      }).then((res) => {
        setGalleryData(res.data.data)
        setPostCount(res.data.pageInfo.totalElements)
      }).catch ((err) => {
        console.error(err);
      })
    } else if (view === 'scraps') {
      setGalleryData(userInfo.scrapPostingList)
    } else if (view === 'plant') {
      axios({
        method: "get",
        url: `http://13.124.33.113:8080/myplants/${currentPlantData.myPlantsId}/gallery?page=1&size=10`,
        headers: {
          Authorization: jwt,
        },
      }).then((res) => {
        console.log(res.data.data)
        setGalleryData(res.data.data)
      }).catch ((err) => {
        console.error(err);
      })
    }
  }

  const setViewData = (postingId) => {
    axios({
      method: "get",
      url: `http://13.124.33.113:8080/posts/${postingId}`,
      headers: {
        Authorization: jwt,
      },
    }).then((res) => {
      handleModal(res.data.data)
    }).catch ((err) => {
      console.error(err);
    })
  }

  useEffect(() => {
    getGalleryData(currentView)
  }, [currentView])
  return (
    <StyledContainer>
      {galleryData.length !== 0 ? (
        <StyledMyPageGallery>
          {galleryData.map((el) => {
            if (currentView === "plant") {
              return (           
                <div className="image-wrapper" key={el.galleryId}>
                  <img className="image" src={el.plantImage} alt="each item" />
                </div>
              );
            } else if (currentView === "postings") {
              return (
                <div className="image-wrapper" key={el.postingId} onClick={() => handleModal(el)}>
                  <img className="image" src={el.postingMedias[0].mediaUrl} alt="each item" />
                </div>
              );
            } else if (currentView === "scraps") {
              return (
                <div className="image-wrapper" key={el.postingId} onClick={() => setViewData(el.postingId)}>
                  <img className="image" src={el.postingMedias[0].mediaUrl} alt="each item" />
                </div>
              )
            }
          })}
        </StyledMyPageGallery>
      ) : (
        <StyledNoContents>
          <FcAnswers />
          <p>게시물이 없습니다</p>
        </StyledNoContents>
      )}
    </StyledContainer>
  );
};

export default Gallery;
