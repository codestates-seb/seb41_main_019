import styled from "styled-components";
import { AiFillHeart } from "react-icons/ai";

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

const Recommend = ({ allPost, followPost }) => {
    return (
        <>
            { allPost ? 
                <StyledLi>
                    <img src={allPost.postingMedias[0].mediaUrl} alt="img" />
                    <span>{allPost.userName}</span>
                    <span><AiFillHeart />+{allPost.likeCount}</span>
                </StyledLi>
                : null
            } 
            { followPost ? 
                <StyledLi>
                    <img src={followPost.postingMedias[0].mediaUrl} alt="img" />
                    <span>{followPost.userName}</span>
                    <span><AiFillHeart />+{followPost.likeCount}</span>
                </StyledLi>
                : null
            }
        </>
    )
};

export default Recommend;