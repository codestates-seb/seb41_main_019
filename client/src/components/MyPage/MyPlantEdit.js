import styled from "styled-components";
import { useState } from "react";
import { BsThreeDotsVertical } from "react-icons/bs";
import { TbCameraPlus } from "react-icons/tb";
import axios from "axios";

import Cookie from "../../util/Cookie";
import AddPlantImage from "./AddPlantImage";

const StyledContainer = styled.div`
  display: flex;

  > p {
    display: flex;
    margin: 0px;
    cursor: pointer;
  }

  svg {
    margin: 10px 0px 0px 3px;
    width: 20px;
    height: 20px;
  }
`;

const StyledMenuContainer = styled.div`
  position: absolute;
  top: 40px;
  right: -100px;
  display: flex;
  flex-direction: column;
  width: 100px;
  height: 100px;
  border: 1px solid #dbdbdb;
  border-radius: 5px;
  box-shadow: 5px 5px 10px 1px rgba(0, 0, 0, 0.3);

  @media screen and (max-width: 770px) {
    position: absolute;
    top: 40px;
    right: 0;
    z-index: 400;
  }
`;
const StyledMenuItem = styled.div`
  z-index: auto;
  display: flex;
  height: 100%;
  background-color: white;
  border: 0;
  border-bottom: 1px solid #dbdbdb;
  font-size: 14px;
  justify-content: center;
  align-items: center;
  cursor: pointer;

  :hover {
    opacity: 1;
  }
`;

const MyPlantEdit = ({ handleChange, handleUpdateMode, currentPlantData, havePlantDeleted, currentView, setCurrentView }) => {
  const cookie = new Cookie();
  const jwt = cookie.get("authorization")

  const [isAddModalOn, setIsAddModalOn] = useState(false);
  const [isEditModalOn, setIsEditModalOn] = useState(false);
  const handleEditModal = () => {
    setIsAddModalOn(false)
    setIsEditModalOn(!isEditModalOn);
  };

  const handleAddClick = () => {
    setIsEditModalOn(false)
    setIsAddModalOn(!isAddModalOn);
  };
  const handleUpdateClick = () => {};
  const handleDeleteClick = () => {
    axios({
      method: "delete",
      url: `${process.env.REACT_APP_API}/myplants/${currentPlantData.myPlantsId}`,
      headers: {
            "Authorization" : jwt
      },
    }).then(res => {
      alert("삭제되었습니다")
      havePlantDeleted();
      handleEditModal();
      handleChange();
    }).catch(
      e => {
        console.error(e)
      }
    )
  };
  const handleDnDClick = () => {
    alert("구현 예정")
  };

  return (
    <StyledContainer>
      <p onClick={handleAddClick}>
        <TbCameraPlus />
      </p>
      <p onClick={handleEditModal}>
        <BsThreeDotsVertical />
      </p>
      {isAddModalOn && <AddPlantImage handleAddClick={handleAddClick} currentPlantId={currentPlantData.myPlantsId} currentView={currentView} setCurrentView={setCurrentView}/>}
      {isEditModalOn && (
        <StyledMenuContainer>
          <StyledMenuItem onClick={handleUpdateMode}>식물 수정</StyledMenuItem>
          <StyledMenuItem onClick={handleDeleteClick}>식물 삭제</StyledMenuItem>
          <StyledMenuItem onClick={handleDnDClick}>앨범 편집</StyledMenuItem>
          <StyledMenuItem onClick={handleEditModal}>취소</StyledMenuItem>
        </StyledMenuContainer>
      )}
    </StyledContainer>
  );
};

export default MyPlantEdit;
