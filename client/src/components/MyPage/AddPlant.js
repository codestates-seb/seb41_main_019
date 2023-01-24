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

const AddPlant = ({ handleModal, userInfo, jwt, getMyPlantsData }) => {
  const [form, setForm] = useState({
    plantName: "",
    // plantType: "",
    // plantBirthday: "",
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
        "plantName" : plantName
        // "plantType" : plantType
        // "plantBirthday" : plantBirthday
      }
    }).then(res => {
      handleModal("AddPlant");
      axios({
        method: "get",
        url : `http://13.124.33.113:8080/myplants/${userInfo.memberId}`,
        headers: {
          "Authorization" : jwt
        }
      }).then(
        (res) => {
          getMyPlantsData();
        }
      )
    })
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
            placeholder="반려식물의 이름을 입력하세요"
            value={plantName}
            onChange={handleInputChange}
          />
          {/* <input
            type="text"
            name="plantType"
            placeholder="종류"
            value={plantType}
            onChange={handleInputChange}
          />
          <input
            type="text"
            name="plantBirthday"
            placeholder="처음 만난 날"
            value={plantBirthday}
            onChange={handleInputChange}
          /> */}
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
