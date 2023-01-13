import styled from "styled-components";
import B from "../../assets/img/plants/알보1.png";

const StyledComments = styled.div`
    height: 80%;
    background-color: white;

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

const Comments = () => {
    return (
        <>
            <StyledComments>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!fdfsdfdsfsdfsdfffffffffffffffffffffff ffffffffffffffffffffffffffffffffff</span>
                    <span>2023-1-13-10:30</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                    <span>2023-1-13-10:30</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                    <span>2023-1-13-10:30</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                    <span>2023-1-13-10:30</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                    <span>2023-1-13-10:30</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                    <span>2023-1-13-10:30</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                    <span>2023-1-13-10:30</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                    <span>2023-1-13-10:30</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                    <span>2023-1-13-10:30</span>
                </div>
                <div className="comment">
                    <img src={B} alt="commentImg" />
                    <span>user1</span>
                    <span>안녕하세요!</span>
                    <span>2023-1-13-10:30</span>
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