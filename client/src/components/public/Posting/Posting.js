import styled from "styled-components";
import Upload from "./Upload";
import Tags from "./Tags";
import CloseBtn from "../CloseBtn";
import { useEffect } from "react";
import { BlueBtn } from "../BlueBtn";

const Wrapper= styled.div`
    background-color: white;
    position: absolute;
    top:50%;
    left:50%;
    transform:translate(-50%, -50%);
    display: flex;
    flex-direction: column;
    gap: 30px;
    width: 800px;
    height: auto;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
    margin: 0 auto;
    padding: 50px;
    color: gray;
    z-index: 1000;

    > div:first-child {
        margin: 0 0 0 auto;
    }

    @media screen and (max-width: 1255px) {
        width: 700px;
    }

    @media screen and (max-width: 770px) {
        width: 500px;
    }
`;

const StyledTextarea = styled.textarea`
    width: 100%;
    height: 200px;
    line-height: 1;
    resize: none;
    padding: 10px 10px;
    outline: none;
    border: 1px solid #dbdbdb;
    border-radius: 5px;

    :focus {
        box-shadow: 0 0 6px #5e8b7e;
    }
`;
// 기능 추가: 사진 x클릭시 사진 삭제, 태그 줄 자동바꿈,
const Posting = ({ handleIsPosted }) => {

    useEffect(() => {
        document.getElementById("bg").addEventListener("click", () => {
            handleIsPosted();
        })
    },[handleIsPosted])

    return (
        <Wrapper>
            <CloseBtn handleModal={handleIsPosted}/>
            <Upload />
            <StyledTextarea placeholder="당신의 식물을 소개해주세요." />
            <Tags />
            <BlueBtn>올리기</BlueBtn>
        </Wrapper>
    )
}

export default Posting;