import { useState } from "react";
import styled from "styled-components"
import { BlueBtn } from "../public/BlueBtn";
import axios from 'axios';
import Cookie from "../../util/Cookie";

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

        img {
            width: 50px;
            height: 50px;
            background-color: gray;
            border-radius: 50px;
        }
    }

    p {
        margin: 0px 100px;
        line-height: 1.5;
        @media screen and (max-width: 770px) {
            width: 90%;
        }
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

    @media screen and (max-width: 770px) {
        font-size: 13px;
    }
`;

const DeleteProfile = ({ name, img, open }) => {
    const [isChecked, setIsChecked] = useState(false);
    const cookie = new Cookie();

    const deleteMember = () => {
        axios({
            method: "delete",
            url: `${process.env.REACT_APP_API}/members/${cookie.get("memberId")}`,
            headers: { Authorization : cookie.get("authorization") }
        }).then(res => {
            open();
        })
        .catch(e => {
           console.log(e);
        });
    };

    const handleBtn = () => {
        setIsChecked(!isChecked);
    };

    return (
        <Wrapper>
            <div>
                <img src={img} alt="img" />
                <span>{name}</span>
            </div>
            <p>게시하신 글이나 댓글은 자동으로 삭제되며, 사용하고 계신 아이디 <span>{name}</span>는 탈퇴할 경우 복구가 불가능합니다.</p>
            <div className="check">
                <input id="checkbox" type="checkbox" value={isChecked} onClick={handleBtn}/>
                <label htmlFor="checkbox">안내 사항을 모두 확인하였으며, 이에 동의합니다.</label>
            </div>
            <BlueBtn disabled={isChecked ? false : true} onClick={() => {
                deleteMember();
            }}>계정 삭제</BlueBtn>
        </Wrapper>
    )
};

export default DeleteProfile;