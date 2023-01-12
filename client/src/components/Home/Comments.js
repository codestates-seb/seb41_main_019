import styled from "styled-components";
import B from "../../assets/img/plants/알보1.png";

const StyledComments = styled.div`
    height: 80%;

    .comment {
        display: flex;
        padding: 10px;
        align-items: center;
    }

    img {
        width: 40px;
        border-radius: 50px;
        cursor: pointer;
    }

    span {
        margin: 0px 5px;
    }
`;

const StyledMyComments = styled.div`
    height: 10%;
`;

const StyledInput = styled.input`
    width: 90%;
    height: 100%;
    border: 2px solid blue;
`;

const StyledButton = styled.button`
    width: 10%;
    height: 100%;
`;

const Comments = () => {
    return (
        <>
            <StyledComments>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!fdfsdfdsfsdfsdfffffffffffffffffffffff ffffffffffffffffffffffffffffffffff</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                </div>
            </StyledComments>
            <StyledMyComments>
                <StyledInput placeholder="Add a comment..."></StyledInput>
                <StyledButton>Post</StyledButton>
            </StyledMyComments>
        </>
    )
}

export default Comments;