import styled from "styled-components"
import { BlueBtn } from "../public/BlueBtn";

const Wrapper = styled.div`
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 50px;
    gap: 30px;

    input {
        width: 150px;
    }

    div {
        display: flex;
        flex-direction: row;

        label {
            width: 120px;
        }
    }

    .profile {
        display: flex;
        flex-direction: row;
        align-items: center;
    }

    .profileImg {
        width: 50px;
        height: 50px;
        background-color: gray;
        border-radius: 50px;
    }
`;

const EditPw = () => {
    return (
        <Wrapper>
            <div>
                <label htmlFor="currentPw">이전 비밀번호</label>
                <input id="currentPw" />
            </div>
            <div>
                <label htmlFor="newPw">새 비밀번호</label>
                <input id="newPw" />
            </div>
            <div>
                <label htmlFor="confirm">새 비밀번호 확인</label>
                <input id="confirm" />
            </div>
            <BlueBtn>변경하기</BlueBtn>
        </Wrapper>
    )
};

export default EditPw;