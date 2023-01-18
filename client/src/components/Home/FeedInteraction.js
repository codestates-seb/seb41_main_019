import { AiOutlineHeart, AiOutlineShareAlt } from "react-icons/ai";
import { BsBookmarkPlus } from "react-icons/bs";
import styled from "styled-components";

const StyledInteraction = styled.div`
    background-color: white;

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

const FeedInteraction = ({ setModal, type=null, post }) => {
    console.log(post);
    return (
        <StyledInteraction>
            <div className="interact">
                <AiOutlineHeart />
                <AiOutlineShareAlt />
                <BsBookmarkPlus />
            </div>
            <p>좋아요 {post.likeCount}개</p>
            <p>{post.postingContent}</p>
            <div className="tags">
                { post.tags ? 
                    post.tags.map((tag, idx) => {
                        return <span key={idx}>#{tag.tagName}</span> 
                    })
                    : null
                }
            </div>
            { type ? null
            : <span onClick={setModal ? () => setModal(true) : null} >댓글 보기 및 댓글쓰기</span>
            }
        </StyledInteraction>
    )
}

export default FeedInteraction;