import { useState } from "react";
import styled from "styled-components"
import { BlueBtn } from "../public/BlueBtn";
import axios from 'axios';

const Wrapper = styled.div`
    width: 100%;
    display:flex;
    flex-direction: column;
    align-items: center;
    gap: 30px;
    margin-top: 50px;

    div {
        display: flex;
        flex-direction: column;
        gap: 5px;
    }

    div:first-of-type{
        display: flex;
        flex-direction: row;
        align-items: center;
        gap: 30px;

        .profileImg {
            width: 50px;
            height: 50px;
            background-color: gray;
            border-radius: 50px;
        }
    }

    p {
        margin: 0px 100px;
        line-height: 1.5;
    }

    .check {
        display: flex;
        flex-direction: row;

        input {
            width: 15px;
            height: 15px;
        }
    }

    button {
        background-color: #D96846;

        :disabled {
            background-color: #D96846;
            opacity: 0.5;
            cursor: default;
            
            :hover {
                transform: scale(1);
            };
        }
    }
`;

const DeleteAccount = () => {
    const [isChecked, setIsChecked] = useState(false);

    const handleDelete = () => {
        axios
            .delete("http://localhost.com/",{})
            .then((res) => {
                alert('계정이 삭제되었습니다');
            })
            .catch(() => {
                console.log('삭제 요청 실패')
            })
    }

    const handleBtn = () => {
        setIsChecked(!isChecked);
    } 

    return (
        <Wrapper>
            <div>
                <div className="profileImg"></div>
                <span>user1</span>
            </div>
            <p>게시하신 글이나 댓글은 자동으로 삭제되며, 사용하고 계신 아이디 <span>user1</span>는 탈퇴할 경우 복구가 불가능합니다.</p>
            <div className="check">
                <input id="checkbox" type="checkbox" value={isChecked} onClick={handleBtn}/>
                <label htmlFor="checkbox">안내 사항을 모두 확인하였으며, 이에 동의합니다.</label>
            </div>
            <BlueBtn disabled={isChecked ? false : true}>계정 삭제</BlueBtn>
        </Wrapper>
    )
};

export default DeleteAccount;