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

const Gallery = ({ currentView, handleModal, userInfo, setPostCount }) => {
  const cookie = new Cookie();
  const jwt = cookie.get("authorization")
  const memberId = Number(cookie.get("memberId"));
  const [galleryData, setGalleryData] = useState([])
  
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
      setGalleryData([])
    }
  }

  useEffect(() => {
    getGalleryData(currentView)
  }, [currentView])
  return (
    <StyledContainer>
      {galleryData ? (
        <StyledMyPageGallery>
          {galleryData.map((el) => {
            if (currentView === "plant") {
              return (           
                <div className="image-wrapper" key={el.imgId}>
                  <img className="image" src={el.imgUrl} alt="each item" />
                </div>
              );
            } else {
              return (
                <div className="image-wrapper" key={el.postingId} onClick={() => handleModal("View", el.postingId)}>
                  <img className="image" src={el.postingMedias[0].mediaUrl} alt="each item" />
                </div>
              );
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
