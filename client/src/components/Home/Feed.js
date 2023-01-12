import styled from "styled-components";
import A from "../../assets/img/plants/1.jpg";
import B from "../../assets/img/plants/알보1.png";
import { AiOutlineMessage } from "react-icons/ai";
import { FiUserPlus } from "react-icons/fi";
import Slider from "./Slider";
import FeedInteraction from "./FeedInteraction";

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
            <FeedInteraction />
        </Wrapper>
    );
}

export default Feed;