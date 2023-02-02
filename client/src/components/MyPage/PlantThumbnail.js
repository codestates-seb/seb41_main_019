import { useState } from "react";
import styled from "styled-components";
import axios from "axios";
import Cookie from "../../util/Cookie";

import defaultIcon from "../../assets/img/plants/defaultPlantIcon.png"

const StyledMyPlantsItem = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 0 20px;
  .selected {
    border: 4px solid #ffb100;
  }
  .image-wrapper {
    width: 80px;
    height: 80px;
    border-radius: 70%;
    overflow: hidden;
    cursor: pointer;

    @media screen and (max-width: 770px) {
      width: 50px;
      height: 50px;
    }
  }
  .image {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  p {
    display: flex;
    justify-content: center;
    width: 100%;
    font-size: 0.8em;
  }

  @media screen and (max-width: 770px) {
    margin: 0px 10px;
  }
`;

const PlantThumbnail = ({handlePlantClick , currentPlantData, el}) => {
    const cookie = new Cookie();
    const jwt = cookie.get("authorization")

    const [imgSrc, setImgSrc] = useState(defaultIcon)

    const getThumbnailImg = async (galleryId) => {
        return axios({
          method: "get",
          url: `${process.env.REACT_APP_API}/myplants/gallery/${galleryId}`,
          headers: {
            "Authorization" : jwt
          },
        })
        .then((res) => {
          setImgSrc(res.data.data.plantImage)
        }).catch((err) => console.error(err))
      };

    if (el.galleryList.length !== 0) {
        const lastGalleryId = el.galleryList[0].galleryId
        getThumbnailImg(lastGalleryId)
      }

    return (
    <StyledMyPlantsItem onClick={() => handlePlantClick(el)}> 
        <div
        className={
            currentPlantData &&
            currentPlantData.myPlantsId === el.myPlantsId
            ? "selected image-wrapper"
            : "image-wrapper"
        }
        >
        <img
            className="image"
            src={imgSrc}
            alt="each item"
        />
        </div>
        <p>{el.plantName}</p>
    </StyledMyPlantsItem>)
}

export default PlantThumbnail