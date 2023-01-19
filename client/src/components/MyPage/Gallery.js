import styled from "styled-components";

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

const Gallery = ({ galleryData, currentView }) => {
  // 반려식물들의 각 사진들의 imgId는 고유한 값이어야 함.
  // 그렇지 않을 경우 key prop 에러 발생
  return (
    <StyledContainer>
      {galleryData.length ? (
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
                <div className="image-wrapper" key={el.postingId}>
                  <a href="#">
                    <img className="image" src={el.imgUrl} alt="each item" />
                  </a>
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
