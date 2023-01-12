import { AiOutlineHeart, AiOutlineShareAlt } from "react-icons/ai";
import { BsBookmarkPlus } from "react-icons/bs";
import styled from "styled-components";

const StyledInteraction = styled.div`

    p {
        margin: 0;
    }

    .interact {
        width: 100%;
        margin: 10px 0px;
    }

    div svg {
        margin-right: 15px;
    }

    p:nth-child(2) {
        font-weight: 600;
        font-size: 16px;
        margin-bottom: 10px;
        color: #222426;
        
    }

    .tags {
        margin: 10px 0px 10px 0px;
        color: #007AC9;
        cursor: pointer;

        span {
            margin-right: 5px;
        }
    }

    > span:last-child {
        color: gray;
        cursor: pointer;
        font-size: 14px;
        letter-spacing: 1px;
    }
`;


const FeedInteraction = ({ setModal }) => {
    return (
        <StyledInteraction>
            <div className="interact">
                <AiOutlineHeart />
                <AiOutlineShareAlt />
                <BsBookmarkPlus />
            </div>
            <p>좋아요 700개</p>
            <p>제가 키우는 몬스테라 알보에요!</p>
            <div className="tags">
                <span>#식테크</span> 
                <span>#몬스테라알보</span>
            </div>
            <span onClick={setModal ? () => setModal(true) : null} >댓글 보기 및 댓글쓰기</span>
        </StyledInteraction>
    )
}

export default FeedInteraction;