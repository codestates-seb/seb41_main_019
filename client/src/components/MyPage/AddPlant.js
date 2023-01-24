import styled from "styled-components";
import { useEffect, useState } from "react";
import axios from "axios";

import CloseBtn from "../public/CloseBtn";
import { BlueBtn } from "../public/BlueBtn";

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
  padding: 20px;

  > form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    > label {
      font-size: 14px;
      color: gray;
      position: relative;
      top: 53px;
      padding: 0px 0px 0px 6px;
      cursor: text;
      transition: top 0.2s ease;
    }
    :focus-within label{
      top: 20px;
    }

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
    > div {
      button:last-of-type {
        background-color: #d96848;
      }
    }
  }
`;

const StyledModalHeader = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  > div {
    margin: 0 0 0 auto;
  }
  > p {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
`;

const AddPlant = ({ handleModal, userInfo, jwt, setGalleryData }) => {
  const [form, setForm] = useState({
    plantName: "",
    plantType: "",
    plantBirthday: "",
  });
  useEffect(() => {
    document.getElementById("bg").addEventListener("click", () => {
      handleModal("AddPlant");
    });
  }, [handleModal]);

  const { plantName, plantType, plantBirthday } = form

  const handleInputChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios({
      method: "post",
      url: "http://13.124.33.113:8080/myplants",
      headers: {
        "Authorization" : jwt
      },
      data: {
        "memberId" : userInfo.memberId,
        "plantName" : plantName,
        "plantType" : plantType,
        "plantBirthDay" : plantBirthday
      }
    }).then(res => {
      handleModal("AddPlant")
    }).catch(
      e => {
        console.error(e)
      }
    )
  };

  return (
    <>
      <StyledContainer>
        <StyledModalHeader>
          <CloseBtn className="close-button" handleModal={handleModal} />
          <p>반려식물 등록하기</p>
        </StyledModalHeader>
        <form onSubmit={handleSubmit}>
          <label htmlFor="plantName">반려식물 이름</label>
          <input
            id="plantName"
            type="text"
            name="plantName"
            value={plantName}
            onChange={handleInputChange}
          />
          <label htmlFor="plantType">종류</label>
          <input
            id="plantType"
            type="text"
            name="plantType"
            value={plantType}
            onChange={handleInputChange}
          /> 
          <label htmlFor="plantBirthday">데려온 날짜</label>
          <input
            id="plantBirthday"
            type="text"
            name="plantBirthday"
            value={plantBirthday}
            onChange={handleInputChange}
          />
          <div className="button-container">
            <BlueBtn type="submit">완료</BlueBtn>
            <BlueBtn onClick={() => handleModal("AddPlant")}>취소</BlueBtn>
          </div>
        </form>
      </StyledContainer>
    </>
  );
};

export default AddPlant;
