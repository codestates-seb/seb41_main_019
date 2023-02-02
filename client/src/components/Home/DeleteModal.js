import styled from "styled-components";
import { useEffect } from "react";
import { FiAlertCircle } from "react-icons/fi";
import { BlueBtn } from "../public/BlueBtn.js";
import axios from "axios";
import Cookie from "../../util/Cookie.js";

const Wrapper = styled.div`
    width: 100%;
    height: 100%;
    overflow: hidden;
    position: fixed;
    top: 0;
    left: 0;
    background-color: rgba(0,0,0,0.3);
    z-index: 1000;
`

const Modal = styled.div`
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

const DeleteModal = ({ handleModal, handleDelete, postId, handleChange }) => {
    const cookie = new Cookie();

    useEffect(() => {
        document.getElementById("bg").addEventListener("click", () => {
            handleDelete();
        })
    },[handleDelete])

   

    const DeletePost = () => {
            axios({
                method: "delete",
                url: `${process.env.REACT_APP_API}/posts/${postId}`,
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
            <Modal>
                <div>
                    <FiAlertCircle />
                    <span> 정말 이 게시물을 삭제하시겠습니까?</span>
                </div>
                <div className="buttons">
                    <BlueBtn 
                        onClick={() => {
                            DeletePost()
                            handleDelete()
                            handleModal(false)
                        }}>Yes</BlueBtn>
                    <BlueBtn onClick={handleDelete}>No</BlueBtn>
                </div>
            </Modal>
        </Wrapper>
    )
};

export default DeleteModal;