import styled from "styled-components"
import { BlueBtn } from "../public/BlueBtn";

const Wrapper = styled.div`
    .profile {
        display: flex;
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
            <div className="profile">
                <div className="profileImg"></div>
                <span>user1</span>
            </div>
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