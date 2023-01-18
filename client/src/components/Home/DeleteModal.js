import styled from "styled-components";
import { useEffect } from "react";
import { FiAlertCircle } from "react-icons/fi";
import { BlueBtn } from "../public/BlueBtn.js";
import axios from "axios";
import Cookie from "../../util/Cookie.js";

const Wrapper = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 20px;
    position: fixed;
    top:50%;
    left:50%;
    transform:translate(-50%, -50%);
    background-color: white;
    z-index: 1000;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
    box-shadow: 5px 10px 10px 1px rgba(0,0,0,0.3); 
    padding: 35px 25px;

    > div {
        display: flex;
        align-items: center;
        gap: 7px;

        svg {
            font-size: 25px;
        }
    }

    .buttons {
        display: flex;
        
        button {
            border: 0;
            cursor: pointer;
            color: #ededed;
            width: 70px;
            height: 30px;
            border-radius: 5px;
            box-shadow: 1px 3px 8px -2px rgb(90, 90, 90);
        }

        button:last-child {
            background-color: #D96848;
            margin-right: 0px;
        }
    }
`;

const DeleteModal = ({ handleDelete, postId, handleChange }) => {
    const cookie = new Cookie();

    useEffect(() => {
        document.getElementById("bg").addEventListener("click", () => {
            handleDelete();
        })
    },[handleDelete])

    const deletePost = () => {
        axios({
            method: "delete",
            url: `http://13.124.33.113:8080/posts/${postId}`,
            headers: { Authorization: cookie.get("authorization") }
            }).then(res => {
                handleChange();
            })
            .catch(e => {
               console.log(e);
            });
    };

    return (
        <Wrapper>
            <div>
                <FiAlertCircle />
                <span> 정말 이 게시물을 삭제하시겠습니까?</span>
            </div>
            <div className="buttons">
                <BlueBtn onClick={deletePost}>Yes</BlueBtn>
                <BlueBtn onClick={handleDelete}>No</BlueBtn>
            </div>
        </Wrapper>
    )
};

export default DeleteModal;