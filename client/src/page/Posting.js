import styled from "styled-components";
import Upload from "../components/Posting/Upload";
import Tag from "../components/Posting/Tag";
import { useNavigate } from "react-router-dom";

const Wrapper= styled.div`
    position: absolute;
    top:50%;
    left:50%;
    transform:translate(-50%, -50%);
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 800px;
    height: 800px;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
    margin: 0 auto;
    padding: 50px;
    color: gray;

    button {
        width: 100px;
        height: 40px;
        border: 0px;
        border-radius: 5px;
        cursor: pointer;
        margin-right: 10px;
        box-shadow: 1px 3px 8px -2px rgb(90, 90, 90);
    }

    .enroll {
        background-color: #2F4858;
        color: white;
    }
    
    .cancel {
        background-color: #D96848;
        color: white;
    }
`;

const StyledTextarea = styled.textarea`
    width: 100%;
    height: 200px;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
    padding-left: 10px;
`;

const Posting = () => {
    const navigate = useNavigate();

    const handleCancel = () => {
        alert("취소되었습니다.")
        navigate("/");
    };

    return (
        <>
            <Wrapper>
                <Upload />
                <StyledTextarea placeholder="당신의 식물을 소개해주세요."/>
                <Tag />
                <div>
                    <button className="enroll">등록</button>
                    <button className="cancel" onClick={handleCancel}>취소</button>
                </div>
            </Wrapper>
        </>
    )
}

export default Posting;