import styled from "styled-components";
import axios from "axios";
import Cookie from "../../util/Cookie";
import { useState } from "react";
import defaultImg from "../../assets/img/profile.jpg";
import { BsPencilFill } from "react-icons/bs";
import { AiOutlineHeart, AiFillHeart } from "react-icons/ai";
import CloseBtn from "../public/CloseBtn";

const StyledComments = styled.ul`
    height: 100%;
    background-color: white;
    overflow: scroll;
    list-style: none;
    padding: 0;
    margin: 0;
    ::-webkit-scrollbar {
        display: none;
    }

    li span {
        margin-right: 5px;
    }

    .comment {
        display: flex;
        padding: 10px;
        align-items: center;
    }

    img {
        width: 8%;;
        border-radius: 50px;
        cursor: pointer;
        margin-right: 5px;
    }

    li .commetContent {
        display: flex;
        flex-direction: column;
        width: 80%;

        span:last-of-type{
            margin-top: 5px;
            color: gray;
            font-size: 12px;
        }
    }

    li .setting {
        display: flex;

        svg {
            width: 10px;
            height: 20px;
            cursor: pointer;
        }
    }

    @media screen and (max-width: 1024px) {
        overflow: auto;
        height: 200px;
        display: flex;
        flex-direction: column-reverse;
    }
`;

const StyledMyComments = styled.div`
    height: 5%;

    @media screen and (max-width: 1024px) {
        height: 10%;
    }
`;

const StyledInput = styled.input`
    width: 85%;
    height: 90%;
    outline: none;
    border: 1px solid #5e8b7e;
    border-radius: 30px;
    font-size: 15px;
    padding: 0px 5px 0px 10px;
    margin: 0px 0px 10px 10px;
    
        :focus { 
            box-shadow: 0 0 6px #5e8b7e;
        }
`;

const StyledButton = styled.button`
    width: 10%;
    height: 100%;
    border: 0px;
    background-color: white;
    color: #5E8B7E;
    cursor: pointer;
`;

const Comments = ({ post, handleChange }) => {
    const [value, setValue] = useState("");
    const cookie = new Cookie();

    const handleValue = (e) => {
        setValue(e.target.value);
    };

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
                setValue("");
                handleChange();
            })
            .catch(e => {
                console.log(e);
            });
    };


    const deleteComment = (commentId) => {
        axios({
            method: "delete", 
            url: `http://13.124.33.113:8080/comments/${commentId}`,
            headers: { Authorization: cookie.get("authorization") }
            }).then(res => {
                console.log("댓글 삭제 성공");
                handleChange();
            })
            .catch(e => {
                console.log(e);
            });
    };

    const updateComment = (commentId) => {
        axios({
            method: "patch",
            url: `http://13.124.33.113:8080/comments/${commentId}`,
            headers: { Authorization: cookie.get("authorization") },
            data: {
                "commentId" : commentId,
                "comment" : "댓글 수정 test"
            }
        }).then(res => {
            console.log("댓글 수정 성공");
            handleChange();
        }).catch(e => {
            console.log(e);
        })
    }

    return (
        <>
            <StyledComments>
                {
                    post.comments ?
                        post.comments.map((comment,idx) => {
                            return (
                                <li key={idx} className="comment">
                                    <img src={comment.profileImage === null ? defaultImg : comment.profileImage} alt="commentImg" />
                                    <span>{comment.userName}</span>
                                    <div className="commetContent">
                                        <span>{comment.comment}</span>
                                        <span>{comment.modifiedAt.replace('T', ' ')}</span>
                                    </div>
                                    { Number(cookie.get("memberId")) === comment.memberId ? 
                                        <div className="setting">
                                            <BsPencilFill onClick={() => updateComment(comment.commentId)}/>
                                            <CloseBtn  handleEvent={() => deleteComment(comment.commentId)} />
                                        </div>
                                        : <div className="setting">
                                            <AiOutlineHeart />
                                        </div>
                                    }
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