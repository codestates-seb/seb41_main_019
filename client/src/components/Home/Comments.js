import styled from "styled-components";
import axios from "axios";
import Cookie from "../../util/Cookie";
import { useState } from "react";
import defaultImg from "../../assets/img/profile.jpg";
import { AiOutlineHeart, AiFillHeart } from "react-icons/ai";
import { exchangeTime } from "../../util/exchangeTime";
import { BiDotsHorizontalRounded } from "react-icons/bi";

const StyledComments = styled.ul`
    display: flex;
    flex-direction: column;
    height: 100%;
    background-color: white;
    overflow: scroll;
    list-style: none;
    padding: 0;
    margin: 0;
    margin-bottom: 0px;
    ::-webkit-scrollbar {
        display: none;
    }
    li {
        margin-bottom: 5px;

        > span:first-of-type {
            display: flex;
            width: 95px;
            margin-right: 5px;
        }
    }

    .comment {
        display: flex;
        height: 38px;
        padding: 0px 10px;

        > span:first-of-type {
            display: flex;
            justify-content: center;
        }
    }

    img {
        width: 24px;
        height: 24px;
        border-radius: 50px;
        cursor: pointer;
        margin-right: 5px;
    }

    .commentContent {
        display: flex;
        flex-direction: column;
        width: 70%;
        
        span {
            overflow-wrap: break-word;
            max-width: 360px;
        }
    }

    .commentInteraction{
        display: flex;
        gap: 5px;
        margin-top: 5px;
        color: gray;
        font-size: 12px;
    }

    li .setting {
        display: flex;
        margin: 0px 0px 0px auto;

        svg {
            width: 15px;
            height: 20px;
            cursor: pointer;
            
            :hover {
            transform: scale(1.2);
            }
        }
    }

    @media screen and (max-width: 1024px) {
        max-height: 400px;
    
        .commentContent {
            width: 330px;
        }
    }
`;

const StyledMyComments = styled.div`
    height: 5%;
    margin-bottom: 10px;
    border-top: 1px solid #dbdbdb;

    @media screen and (max-width: 1024px) {
        height: 15%;
    }
`;

const StyledInput = styled.input`
    width: 85%;
    height: 80%;
    outline: none;
    border: 1px solid #5e8b7e;
    border-radius: 30px;
    font-size: 15px;
    padding: 0px 10px;
    margin: 10px 0px 10px 10px;
    
        :focus { 
            box-shadow: 0 0 6px #5e8b7e;
        }
`;

const StyledButton = styled.button`
    width: 10%;
    height: 100%;
    border: 0px;
    border-radius: 50px;
    background-color: white;
    color: #5E8B7E;
    margin-left: 5px;
    cursor: pointer;
`;

const Comments = ({ post, handleChange, handleCommentMenu, setCommentId }) => {
    const [value, setValue] = useState("");
    const cookie = new Cookie();

    const handleValue = (e) => {
        setValue(e.target.value);
    };

    const addComment = () => {
        axios({
            method: "post",
            url: `${process.env.REACT_APP_API}/comments/${post.postingId}`,
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
            });
    };

    const likeComment = (commentId) => {
        axios({
            method: "post",
            url: `${process.env.REACT_APP_API}/comments/${commentId}/likes`,
            headers: { Authorization: cookie.get("authorization") },
            data: {
                "memberId" : cookie.get("memberId"),
                "commentId" : commentId
            }
        }).then(res => {
            handleChange();
        }).catch(e => {
        })
    }

    const unLikeComment = (likeId) => {
        axios({
            method: "delete", 
            url: `${process.env.REACT_APP_API}/comments/likes/${likeId}`,
            headers: { Authorization: cookie.get("authorization") }
            }).then(res => {
                handleChange();
            })
            .catch(e => {
            });
    };
    
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
                                    <div className="commentContent">
                                        <span>{comment.comment}</span>
                                        <div className="commentInteraction">
                                            <span>{exchangeTime(comment)}</span>
                                            <span>•</span>
                                            <span>좋아요 {comment.likeCount}개</span>
                                        </div>
                                    </div>
                                    <div className="setting">
                                    { Number(cookie.get("memberId")) === comment.memberId ?
                                        <>
                                            <BiDotsHorizontalRounded onClick={() => {
                                                setCommentId(comment.commentId);
                                                handleCommentMenu();
                                            }} />
                                        </> :
                                        <>
                                            { comment.commentLikeList.filter(like => like.memberId === Number(cookie.get("memberId"))).length > 0 ?
                                                <AiFillHeart onClick={() => unLikeComment(comment.commentLikeList.filter(like => like.memberId === Number(cookie.get("memberId")))[0].commentLikeId)} />
                                                : <AiOutlineHeart onClick={() => likeComment(comment.commentId)} />
                                            }
                                        </>
                                    }
                                    </div>
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