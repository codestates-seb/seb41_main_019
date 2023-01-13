import styled from "styled-components";
import { useEffect, useState } from "react";

import { AiOutlineClose } from "react-icons/ai";

const StyledContainer = styled.div`
  display: flex;
  position: absolute;
  justify-content: center;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  flex-direction: column;
  width: 470px;
  height: auto;
  background-color: white;
  z-index: 1000;
`;

const StyledModalHeader = styled.div`
  display: flex;
  width: 100%;
  > p {
    width: 100%;
    display: flex;
    justify-content: center;
  }
  > button {
    border: none;
    background-color: white;
  }
`;

const AddPlant = ({ handleIsCovered }) => {
  const [textInput, setTextInput] = useState({
    plantName: "",
    plantType: "",
    plantBirthday: "",
  });

  const { plantName, plantType, plantBirthday } = textInput;

  useEffect(() => {
    console.log(textInput);
  }, [textInput]);

  const handleInputChange = (e) => {
    setTextInput({ ...textInput, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    alert(JSON.stringify(textInput));
  };

  return (
    <>
      <StyledContainer>
        <StyledModalHeader>
          <p>반려식물 등록하기</p>
          <button onClick={handleIsCovered}>
            <AiOutlineClose />
          </button>
        </StyledModalHeader>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            name="plantName"
            placeholder="식물이름"
            onChange={handleInputChange}
          />
          <input
            type="text"
            name="plantType"
            placeholder="종류"
            onChange={handleInputChange}
          />
          <input
            type="text"
            name="plantBirthday"
            placeholder="처음 만난 날"
            onChange={handleInputChange}
          />
          <button type="submit">완료</button>
        </form>
      </StyledContainer>
    </>
  );
};

export default AddPlant;
