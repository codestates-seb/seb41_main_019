import styled from "styled-components"

import { BsFillCameraFill } from "react-icons/bs";
import { BlueBtn } from "../public/BlueBtn"
import { useRef } from "react";
import { useCallback } from "react";
import axios from "axios";
import Cookie from "../../util/Cookie";
import { useState } from "react";
import { useEffect } from "react";

const StyledContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: absolute;
    background-color: white;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
    box-shadow: 5px 5px 10px 1px rgba(0, 0, 0, 0.3);
    width: 220px;
    height: 340px;
    top: -200px;
    right: -300px;
    padding: 20px;

    > p {
        display: flex;
        width: 140px;
        font-size: 0.9em;
        margin: 5px;
    }
    > input {
        width: 140px;
        margin-bottom: 20px;
    }

    .button-container {
        display: flex;
        width: 220px;
        transform: translate(20px, 10px);
        > button:last-of-type {
            background-color: #d96848;
        }
    }

    @media screen and (max-width: 770px) {
        position: absolute;
        top: 40px;
        right: 0;
        z-index: 300;
    }
`

const StyledInputWrapper = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
    height: 140px;
    width: 140px;
    margin-bottom: 10px;
    cursor: pointer;

    > input {
        display: none;
    }


    :hover {
        opacity: 0.7;

        svg {
            color: black;
        }
    }

    img {
        width: 100%;
        height: 100%;
    }

    svg {
        width: 50px;
        height: 50px;
        color: gray;
        @media screen and (max-width: 770px) {
            width: 20px;   
        }
    }

    @media screen and (max-width: 770px) {
        width: 100px;
        height: 100px;
    }
`;

const AddPlantImage = ({handleAddClick, currentPlantId, currentView, setCurrentView}) => {
    const cookie = new Cookie();
    const jwt = cookie.get("authorization")

    const inputRef = useRef(null);
    const [imageInput, setImageInput] = useState(null);
    const [imageFile, setImageFile] = useState(null);
    const [contentInput, setContentInput] = useState("");

    const onSetFormData = useCallback((e) => {
        if (!e.target.files) {
            return;
        }
        setImageFile(e.target.files[0])

        const reader = new FileReader();
        reader.readAsDataURL(e.target.files[0])
        reader.onloadend = () => {
            setImageInput(reader.result)
        }
    }, [])

    const onSubmit = () => {
        if(contentInput.length === 0) {
            alert("사진의 코멘트를 입력해주세요.")
            return;
        }

        const formData = new FormData();
        formData.append("requestBody", new Blob([JSON.stringify({
            "content": contentInput
        })], { type: "application/json"}));
        formData.append("galleryImage", imageFile);

        axios({
            method: "post",
            url: `${process.env.REACT_APP_API}/myplants/${currentPlantId}/gallery`,
            data:  formData,
            headers: {
              Authorization: jwt,
            },
          }).then((res) => {
            handleAddClick();
            if(currentView === "plant") {
                setCurrentView("plantRerender")
            } else {
                setCurrentView("plant")
            }
          }).catch ((err) => {
            console.error(err);
          })
    }
    
    const onContentInputChange = (e) => {
        setContentInput(e.target.value)
    }

    const onImgInputBtnClick = useCallback(() => {
        if (!inputRef.current) {
            return;
        }
        inputRef.current.click();
    }, []);

    return (
        <StyledContainer>
            <p>이미지 업로드</p>
            <StyledInputWrapper onClick={onImgInputBtnClick}>
                {imageInput ? <img src={imageInput} alt="img" /> : <BsFillCameraFill />}
                <input ref={inputRef} type="file" accept="image/*" onChange={onSetFormData} />
            </StyledInputWrapper>
            <p>코멘트</p>
            <input type="text" onChange={onContentInputChange}/>
            <div className="button-container">
                <BlueBtn onClick={onSubmit}>완료</BlueBtn>
                <BlueBtn onClick={handleAddClick}>취소</BlueBtn>
            </div>
        </StyledContainer>
    )
}

export default AddPlantImage