import styled from "styled-components";
import axios from "axios";
import Cookie from "../../util/Cookie";

const Wrapper = styled.div`
    position: fixed;
    top:50%;
    left:50%;
    color: black;
    transform:translate(-50%, -50%);    
    background-color: rgba(0,0,0,0.5);
    z-index: 1100;
    width: 1240px;
    height: 900px;
    border-radius: 5px;

    >div {
        position: fixed;
        top: 50%;
        left: 50%;
        transform:translate(-50%, -50%); 
    }

    button {
        display: block;
        border: 0;
        background-color: white;
        width: 200px;
        height: 50px;
        border-bottom: 1px solid #dbdbdb;
        box-shadow: 5px 5px 10px 1px rgba(0,0,0,0.3);
        margin: 0px auto;
    }

    @media screen and (max-width: 1255px) {
        width: 900px;
        height: 900px;
    }

    @media screen and (max-width: 1024px) {
        width: 500px;
        height: 900px;
        top: 52%;
    }
`;

const CommentMenu = ({ handleChange, handleCommentMenu, commentId }) => {
    const cookie = new Cookie();

    const deleteComment = (commentId) => {
        axios({
            method: "delete", 
            url: `http://13.124.33.113:8080/comments/${commentId}`,
            headers: { Authorization: cookie.get("authorization") }
            }).then(res => {
                handleChange();
                handleCommentMenu();
            })
            .catch(e => {
                console.log(e);
            });
    };

    return (
        <Wrapper>
            <div className="menu">
                <button onClick={() => deleteComment(commentId)}>삭제하기</button>
                <button onClick={handleCommentMenu}>취소</button>
            </div>
        </Wrapper>
    )
};

export default CommentMenu;