import styled from "styled-components";
import { BlueBtn } from "../public/BlueBtn";

const Wrapper = styled.div`
    display: flex;
    flex-direction: column;

    .profile {
        display: flex;
        flex-direction: row;

        div:nth-of-type(2) {
            display: flex;
            flex-direction: column;
        }
    }
    
    .profileImg {
        width: 50px;
        height: 50px;
        background-color: gray;
        border-radius: 50px;
    }
`;

const EditProfile = () => {
    return (
        <Wrapper>
            <div className="profile">
                <div className="profileImg"></div>
                <div>
                    <span>user1</span>
                    <label htmlFor="profileImg">프로필 사진 바꾸기</label>
                    <input id="profileImg" type="file" hidden />
                </div>
            </div>
            <div>
                <label htmlFor="username">사용자 이름</label>
                <input id="username" />
            </div>
            <div>
                <label htmlFor="introduction">소개</label>
                <textarea id="introduction" />
            </div>
            <div>
                <label htmlFor="email">이메일</label>
                <input id="email" />
            </div>
            <div>
                <label htmlFor="location">지역</label>
                <input id="location" />
            </div>
            <BlueBtn>변경하기</BlueBtn>
        </Wrapper>
    )
};

export default EditProfile;
