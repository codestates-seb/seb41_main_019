import styled from "styled-components";
import { useEffect, useState } from "react";

import CloseBtn from "../public/CloseBtn";

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
  border: 1px solid #dbdbdb;
  border-radius: 5px;
  background-color: white;
  z-index: 1000;

  > form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    > input {
      width: 400px;
      height: 30px;
      margin: 30px;
      border-radius: 5px;
      border: 1px solid #dbdbdb;
      outline: none;
      padding: 10px 10px;
      :focus {
        box-shadow: 0 0 6px #5e8b7e;
      }
    }
    > button {
      width: 100px;
      height: 40px;
      margin: 30px 0;
      border: 0px;
      border-radius: 5px;
      cursor: pointer;
      box-shadow: 1px 3px 8px -2px rgb(90, 90, 90);
      background-color: #2f4858;
      color: white;
    }
  }
`;

const StyledModalHeader = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  > div {
    display: flex;
    flex-direction: row-reverse;
    margin: 20px 20px 0 0;
  }
  > p {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
`;

const AddPlant = ({ handleModal }) => {
  const [textInput, setTextInput] = useState({
    plantName: "",
    plantType: "",
    plantBirthday: "",
  });
  useEffect(() => {
    document.getElementById("bg").addEventListener("click", () => {
      handleModal();
    });
  }, [handleModal]);

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
          <CloseBtn className="close-button" handleModal={handleModal} />
          <p>반려식물 등록하기</p>
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
