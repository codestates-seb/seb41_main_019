import styled from "styled-components";
import axios from "axios";
import Cookie from "../../util/Cookie";

const Wrapper = styled.div`
    display: flex;
    align-items: center;
    flex-direction: column;
    border: 1px solid black;
    position: fixed;
    top:50%;
    left:50%;
    transform:translate(-50%, -50%);    
    width: 200px;
    background-color: white;
    z-index: 1100;
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
            <p onClick={() => deleteComment(commentId)}>삭제하기</p>
            <p onClick={handleCommentMenu}>취소</p>
        </Wrapper>
    )
};

export default CommentMenu;