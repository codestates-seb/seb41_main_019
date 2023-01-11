import styled from "styled-components";
import A from "../../assets/img/plants/11.jpg";
import B from "../../assets/img/plants/알보1.png";
import { AiOutlineHeart, AiOutlineShareAlt, AiOutlineMessage } from "react-icons/ai";
import { BsBookmarkPlus } from "react-icons/bs";
import { FiUserPlus } from "react-icons/fi";
import Slider from "./Slider";

const Wrapper = styled.div`
    width: 470px;
    height: 750px;
    margin-bottom: 20px;
    border-bottom: 1px solid #DBDBDB;

    img {
        width: 100%;
        height: 500px;
        border-radius: 3px;
    }

    svg {
        font-size: 25px;
        color: #222426;
        cursor: pointer;
    }
`;

const StyledHeader = styled.div`
    display: flex;
    align-items: flex-end;
    margin-bottom: 10px;

    img {
        width: 40px;
        height: 40px;
        border-radius: 45px;
        margin-right: 10px;
        cursor: pointer;
    }

    div {
        display: flex;
        flex-direction: column;
    }

    div > span:first-child{
        color: #222426;
        font-size: 16px;
        font-weight: 500;
        margin-bottom: 3px;
        letter-spacing: 1px;
    }

    div > span:nth-child(2) {
        font-size: 12px;
        color: gray;
        letter-spacing: 1px;
    }

    .icons {
        flex-direction: row;
        margin : 0px 0px 5px auto;
    }

    div > svg:first-child {
        margin-right: 15px;
    }
`;

const StyledInteraction = styled.section`

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

const Feed = () => {

    const img = [ A,B ];

    return (
        <Wrapper>
            <StyledHeader>
                <img src={A} alt="img" />
                <div>
                    <span>홍길동</span>
                    <span>7시간 전</span>
                </div>
                <div className="icons">
                    <FiUserPlus />
                    <AiOutlineMessage />
                </div>
            </StyledHeader>
            {  img.length > 1
                ? <Slider img={img} /> 
                : <img src={B} alt="img" />
            }
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
                <span>댓글 보기 및 댓글쓰기</span>
            </StyledInteraction>
        </Wrapper>
    );
}

export default Feed;