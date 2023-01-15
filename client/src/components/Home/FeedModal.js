import styled from "styled-components";
import { useEffect } from "react";

const Wrapper = styled.div`
    display: flex;
    flex-direction: column;
    position: fixed;
    top:50%;
    left:50%;
    transform:translate(-50%, -50%);
    width: 400px;
    height: 200px;
    background-color: white;
    z-index: 1000;
    border: 1px solid #dbdbdb;
    border-radius: 5px;

    button {
        height: 100%;
        background-color: white;
        border: 0;
        border-bottom: 1px solid #dbdbdb;
        font-size: 17px;
        cursor: pointer;
    }
`;

const FeedModal = ({ handleMenu }) => {
    useEffect(() => {
        document.getElementById("bg").addEventListener("click", () => {
            handleMenu();
        })
    },[handleMenu])

    const handleDelete = () => {
        alert('게시글이 삭제되었습니다!')
    }

    return (
        <Wrapper>
            <button>Edit</button>
            <button onClick={handleDelete}>Delete</button>
            <button onClick={handleMenu}>Cancel</button>
        </Wrapper>
    )
};

export default FeedModal;