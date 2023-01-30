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
    width: 100%;
    height: 100%;
    border-radius: 5px;
    animation: modal-bg-show 0.3s;

    @keyframes modal-bg-show {
        from {
            opacity: 0;
        }
        to {
            opacity: 1;
        }
    }

    > div {
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
`;

const CommentModal = ({ handleChange, handleCommentMenu, commentId }) => {
    const cookie = new Cookie();

    const deleteComment = (commentId) => {
        axios({
            method: "delete", 
            url: `${process.env.REACT_APP_API}/comments/${commentId}`,
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
            <div className="menu" onClick={handleCommentMenu} >
                <button onClick={() => deleteComment(commentId)}>삭제하기</button>
                <button onClick={handleCommentMenu}>취소</button>
            </div>
        </Wrapper>
    )
};

export default CommentModal;