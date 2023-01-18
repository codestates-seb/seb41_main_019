import styled from "styled-components";
import A from "../../assets/img/plants/1.jpg";
import B from "../../assets/img/plants/알보1.png";
import { FiUserPlus } from "react-icons/fi";
import { BiDotsVerticalRounded } from "react-icons/bi";
import Slider from "./Slider";
import FeedInteraction from "./FeedInteraction";
import FeedMenu from "./FeedMenu";
import { useState } from "react";

const Wrapper = styled.div`
    position: relative;
    width: 500px;
    height: 730px;
    padding-top: 25px;
    margin-bottom: 20px;
    border-top: 1px solid #DBDBDB;

    img {
        width: 100%;
        height: 100%;
        border-radius: 3px;
    }

    svg {
        font-size: 25px;
        color: #222426;
        cursor: pointer;
    }

    @media screen and (max-width: 770px) {
        width: 460px;
        height: 700px;
    }
`;

const StyledHeader = styled.div`
    width: 100%;
    display: flex;
    align-items: flex-end;
    margin-bottom: 10px;

    img {
        width: 50px;
        height: 50px;
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
        font-size: 14px;
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


const Post = ({ post, handleModal, handleDelete }) => {
    // console.log(post);
    const [menu, setMenu] = useState(false);

    const handleMenu = () => {
        setMenu(!menu);
    }
    
    const img = [ A, B ];

    const exchangeTime = () => {
        //일 차이 계산
        const nDay = new Date().toISOString().slice(0, 10).split("-")[2];
        const pDay = post.modifiedAt.slice(0, 10).split("-")[2];

        const day = (nDay - pDay) * 24 * 60 * 60;

        //시간 차이 계산
        const nTime = new Date().toISOString().slice(11, 19).split(":").map((el, idx) => {
            switch(idx) {
                case 0 : return Number(el) * 60 * 60;
                case 1 : return Number(el) * 60;
                case 2 : return Number(el);
                default : break;
            }
        }).reduce((acc, cur) => acc + cur);

        const pTime = post.modifiedAt.slice(11, 19).split(":").map((el, idx) => {
            switch(idx) {
                case 0 : return Number(el) * 60 * 60;
                case 1 : return Number(el) * 60;
                case 2 : return Number(el);
                default : break;
            }
        }).reduce((acc, cur) => acc + cur);

        // 일 차이 반영
        const differ = nTime - pTime + day;

        // 최대 시간 차로 변환
        const hour = parseInt(differ / 60 / 60);
        const minute = parseInt(differ / 60 % 60);
        const second = differ % 60;

        if(hour > 0) {
            return `${hour}시간 전`;
        } else if(minute > 0) {
            return `${minute}분 전`;
        } else {
            return `${second}초 전`;
        }
    }

    return (
        <Wrapper>
            { menu ? <FeedMenu handleDelete={handleDelete} handleMenu={handleMenu} /> : null }
            <StyledHeader>
                <img src={post.profileImage} alt="img" />
                <div>
                    <span>{post.userName}</span>
                    <span>{exchangeTime()}</span>
                </div>
                <div className="icons">
                    <FiUserPlus />
                    <BiDotsVerticalRounded onClick={handleMenu} />
                </div>
            </StyledHeader>
            {  img.length > 1
                ? <Slider img={img} /> 
                : <img src={B} alt="img" />
            }
            <FeedInteraction setModal={handleModal} />
        </Wrapper>
    );
}

export default Post;