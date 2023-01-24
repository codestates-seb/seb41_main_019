import styled from "styled-components";
import EditProfile from "../components/Setting/EditProfile";
import EditPw from "../components/Setting/EditPw";
import DeleteAccount from "../components/Setting/DeleteAccount";
import { useState } from "react";
import Footer from "../components/public/Footer";

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

    input, textarea {
        width :300px;
        outline: none;
        border: 1px solid #dbdbdb;
        border-radius: 5px;

        :focus {
            outline: none;
            box-shadow: 0 0 6px #5e8b7e;
        }
    }

    input {
        height: 30px;
    }
`;

const StyledMenu = styled.div`
    display: flex;
    width: 30%;
    gap: 20px;
    flex-direction: column;
    border-right: 1px solid #dbdbdb;


    p {
        margin: 0;
        padding: 20px;
    }

    .active {
        border-left: 3px solid #374435;
    }
`

const Setting = () => {
    const [isClicked, setIsClicked] = useState(0);

    return (
        <Wrapper>
            <StyledMenu>
                <p className={isClicked === 0 ? "active" : null} onClick={() => setIsClicked(0)}>프로필 편집</p>
                <p className={isClicked === 1 ? "active" : null} onClick={() => setIsClicked(1)}>비밀번호 변경</p>
                <p className={isClicked === 2 ? "active" : null} onClick={() => setIsClicked(2)}>계정 탈퇴</p>
            </StyledMenu>
            { isClicked === 0 
                ? <EditProfile /> 
                :  isClicked === 1 ? <EditPw /> : <DeleteAccount />
            }
            <Footer />
        </Wrapper>
    )
};

export default Setting;