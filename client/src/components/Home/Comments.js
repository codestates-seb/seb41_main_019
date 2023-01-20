import styled from "styled-components";
import B from "../../assets/img/plants/알보1.png";
import axios from "axios";
import Cookie from "../../util/Cookie";
import { useState } from "react";

const StyledComments = styled.div`
    height: 80%;
    background-color: white;
    overflow: scroll;
    ::-webkit-scrollbar {
        display: none;
    }

    .comment {
        display: flex;
        padding: 10px;
        align-items: center;

        > span:nth-child(4) {
            font-size: 10px;
            width: 20%;
            color: gray;
        }
    }

    img {
        width: 8%;;
        border-radius: 50px;
        cursor: pointer;
    }

    span {
        margin: 0px 5px;
    }  

    @media screen and (max-width: 1024px) {
        overflow: auto;
        height: 100%;
        display: flex;
        flex-direction: column-reverse;
    }
`;

const StyledMyComments = styled.div`
    height: 10%;

    @media screen and (max-width: 1024px) {
        height: 10%;
    }
`;

const StyledInput = styled.input`
    width: 90%;
    height: 100%;
    border-top: 1px solid #dbdbdb;
    font-size: 15px;
`;

const StyledButton = styled.button`
    width: 10%;
    height: 100%;
    border: 0px;
    background-color: white;
`;

const Comments = ({ post }) => {
    const [value, setValue] = useState("");
    const cookie = new Cookie();

    const addComment = () => {
        axios({
            method: "post",
            url: `http://13.124.33.113:8080/comments/${post.postingId}`,
            headers: { Authorization: cookie.get("authorization") },
            data : {
                "memberId" : cookie.get("memberId"),
                "comment" : value
                }
            }).then(res => {
                console.log(res);
            })
            .catch(e => {
            console.log(e);
            });
    };

    const handleValue = (e) => {
        setValue(e.target.value);
    }

    return (
        <>
            <StyledComments>
                {
                    post.comments ?
                        post.comments.map((comment,idx) => {
                            return (
                                <li className="comment">
                                    <img src={B} alt="commentImg" />
                                    <span>user1</span>
                                    <span>{comment.comment}</span>
                                    <span>2023-1-13-10:30</span>
                                </li>
                            )
                        }) : null
                }
            </StyledComments>
            <StyledMyComments>
                <StyledInput value={value} onChange={handleValue} placeholder="Add a comment..."></StyledInput>
                <StyledButton onClick={addComment}>Post</StyledButton>
            </StyledMyComments>
        </>
    )
}

export default Comments;