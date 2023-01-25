import styled from "styled-components";
import { AiFillHeart } from "react-icons/ai";
import { useState } from "react";

const StyledLi = styled.li`
    display: flex;
    flex-direction: column;
    align-items: center;

    img {
        width: 88px;
        height: 88px;
        padding: 10px;
        border-radius: 50px;
        cursor: pointer;
    }

    span {
        font-size: 12px;
        letter-spacing: 1px;
    }

    span:last-of-type {
        display: flex;
        align-items: center;

        svg {
            fill: #F35369;
        }
    }
`;

const Recommend = ({ post, handleModal, setCurPost }) => {
    return (
        <>
            <StyledLi>
                <img src={post.postingMedias[0].mediaUrl} alt="img" onClick={() => {
                    setCurPost(post);
                    handleModal(true);
                }} />
                <span>{post.userName}</span>
                <span><AiFillHeart />+{post.likeCount}</span>
            </StyledLi>
        </>
    )
};

export default Recommend;