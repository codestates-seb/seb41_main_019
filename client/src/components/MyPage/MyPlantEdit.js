import styled from "styled-components";
import { useState } from "react";
import { BsThreeDotsVertical } from "react-icons/bs";

const StyledContainer = styled.div`
  display: flex;
  > p {
    display: flex;
    position: absolute;
    transform: translateX(-50px);
    margin: 20px 0;
    cursor: pointer;
  }
  svg {
    width: 20px;
    height: 20px;
  }
`;

const StyledMenuContainer = styled.div`
  display: flex;
  flex-direction: column;
  position: fixed;
  margin: 20px 0;
`;
const StyledMenuItem = styled.div`
  width: 80px;
  height: 20px;
  background-color: white;
  cursor: pointer;
  transform: translate(-80px, -10px);
  border: 1px solid #dbdbdb;
  text-align: center;
`;

const MyPlantEdit = ({ handleUpdateMode }) => {
  const [isEditModalOn, setIsEditModalOn] = useState(false);
  const handleEditModal = () => {
    setIsEditModalOn(!isEditModalOn);
  };

  const handleUpdateClick = () => {};
  const handleDeleteClick = () => {};
  const handleDnDClick = () => {};

  return (
    <StyledContainer>
      {isEditModalOn ? (
        <StyledMenuContainer>
          <StyledMenuItem onClick={handleUpdateMode}>식물 수정</StyledMenuItem>
          <StyledMenuItem>식물 삭제</StyledMenuItem>
          <StyledMenuItem>앨범 편집</StyledMenuItem>
          <StyledMenuItem onClick={handleEditModal}>취소</StyledMenuItem>
        </StyledMenuContainer>
      ) : (
        <p onClick={handleEditModal}>
          <BsThreeDotsVertical />
          편집
        </p>
      )}
    </StyledContainer>
  );
};

export default MyPlantEdit;
