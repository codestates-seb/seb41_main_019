import styled from "styled-components";
import { useState } from "react";
import { BsThreeDotsVertical } from "react-icons/bs";

const StyledContainer = styled.div`
  display: flex;
  > p {
    display: flex;
    position: absolute;
    transform: translateX(-50px);
    width: 50px;
    margin: 20px 0;
    cursor: pointer;
  }
  svg {
    width: 20px;
    height: 20px;
  }
`;

const StyledMenuContainer = styled.div`
  position: absolute;
  top: 40px;
  right: 0;
  display: flex;
  flex-direction: column;
  width: 100px;
  height: 100px;
  border: 1px solid #dbdbdb;
  border-radius: 5px;
  box-shadow: 5px 5px 10px 1px rgba(0, 0, 0, 0.3);
`;
const StyledMenuItem = styled.div`
  z-index: auto;
  display: flex;
  height: 100%;
  background-color: white;
  opacity: 0.4;
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
      <p onClick={handleEditModal}>
        <BsThreeDotsVertical />
        편집
      </p>
      {isEditModalOn && (
        <StyledMenuContainer>
          <StyledMenuItem onClick={handleUpdateMode}>식물 수정</StyledMenuItem>
          <StyledMenuItem>식물 삭제</StyledMenuItem>
          <StyledMenuItem>앨범 편집</StyledMenuItem>
          <StyledMenuItem onClick={handleEditModal}>취소</StyledMenuItem>
        </StyledMenuContainer>
      )}
    </StyledContainer>
  );
};

export default MyPlantEdit;
