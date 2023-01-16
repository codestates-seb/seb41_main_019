import styled from "styled-components";
import { useEffect } from "react";
import { FiAlertCircle } from "react-icons/fi";

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
    width: 400px;
    height: 100px;
    background-color: white;
    z-index: 1000;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
    box-shadow: 5px 10px 10px 1px rgba(0,0,0,0.3); 

    > div {
        display: flex;
        gap: 20px;

        svg {
            font-size: 20px;
        }
    }

    .buttons {
        display: flex;
        gap: 20px;

        button {
            border: 0;
            cursor: pointer;
            color: #ededed;
            width: 50px;
            height: 30px;
            border-radius: 5px;
            box-shadow: 1px 3px 8px -2px rgb(90, 90, 90);
        }

        button:first-child {
            background-color: #2F4858;
        }

        button:last-child {
            background-color: #D96848;
        }
    }
`;

const DeleteModal = ({ handleDelete }) => {
    useEffect(() => {
        document.getElementById("bg").addEventListener("click", () => {
            handleDelete();
        })
    },[handleDelete])

    return (
        <Wrapper>
            <div>
                <FiAlertCircle />
                <span> 정말 이 게시물을 삭제하시겠습니까?</span>
            </div>
            <div className="buttons">
                <button>Yes</button>
                <button onClick={handleDelete}>No</button>
            </div>
        </Wrapper>
    )
};

export default DeleteModal;