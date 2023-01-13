import Upload from "../components/Posting/Upload";
import styled from "styled-components";

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
    width: 80%;
    height: 200px;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
`;

const StyledTag = styled.input`
    width: 100px;
    height: 40px;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
    cursor: pointer;
    padding: 20px;
`;

const Posting = () => {
    return (
        <>
            <Wrapper>
                <Upload />
                <StyledTextarea placeholder="당신의 식물을 소개해주세요."/>
                <StyledTag placeholder="# 키워드" />
                <div>
                    <button className="enroll">등록</button>
                    <button className="cancel">취소</button>
                </div>
            </Wrapper>
        </>
    )
}

export default Posting;