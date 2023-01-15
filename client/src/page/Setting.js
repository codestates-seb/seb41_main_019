import styled from "styled-components";
import EditProfile from "../components/Setting/EditProfile";
import EditPw from "../components/Setting/EditPw";
import DeleteAccount from "../components/Setting/DeleteAccount";
import { useState } from "react";

const Wrapper = styled.div`
    display: flex;
    justify-content: space-around;
    position: absolute;
    height: 100%;
    width: 800px;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    border-left: 1px solid #dbdbdb;
    border-right: 1px solid #dbdbdb; 
`;

const StyledMenu = styled.div`
    display: flex;
    gap: 20px;
    flex-direction: column;
    border-right: 1px solid #dbdbdb;
    padding: 10px;
`


const Setting = () => {
    const [isClicked, setIsClicked] = useState(0);

    return (
        <Wrapper>
            <StyledMenu>
                <span onClick={() => setIsClicked(0)}>프로필 편집</span>
                <span onClick={() => setIsClicked(1)}>비밀번호 변경</span>
                <span onClick={() => setIsClicked(2)}>계정 탈퇴</span>
            </StyledMenu>
            { isClicked === 0 
                ? <EditProfile /> 
                :  isClicked === 1 ? <EditPw /> : <DeleteAccount />
            }
           
        </Wrapper>
    )
};

export default Setting;