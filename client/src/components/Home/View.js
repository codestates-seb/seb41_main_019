import styled from "styled-components";
import Slider from "./Slider";
import FeedInteraction from "./FeedInteraction";
import Comments from "./Comments";
import { useEffect } from "react";
import CloseBtn from "../public/CloseBtn";

import { exchangeTime } from "../../util/exchangeTime";

const Wrapper = styled.div`
    display: flex;
    position: fixed;
    top:50%;
    left:50%;
    transform:translate(-50%, -50%);
    width: 1240px;
    height: 900px;
    background-color: white;
    z-index: 1000;

    .none {
        display: none;
    }

    p, span, svg {
        margin-left: 10px;
    }

    @media screen and (max-width: 1255px) {
        width: 900px;
        height: 900px;
    }

    @media screen and (max-width: 1024px) {
        width: 500px;
        height: 400px;
        top: 30%;
        flex-direction: column;

        p {
            margin-left: 10px;
        }

        svg {
            font-size: 20px;
        }
    }
`;

const StyledSlider = styled.div`
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    background-color: black;

    @media screen and (min-width: 1024px) {
        > div ul li div:first-child {
            height: 900px;
        }
    }   

    @media screen and (max-width: 1024px) {
        > div ul li div:first-child {
            height: 400px;
        }
    }   
`

const StyledInteraction = styled.div`
    width: 45%;
    display: flex;
    flex-direction: column;
    background-color: white;

    > div:first-child {
        @media screen and (max-width: 1024px){
            padding: 5px;
        }
    }

    .profile {
        padding: 5px;
        display: flex;
        align-items: center;

        img {
            width: 40px;
            height: 40px;
            border-radius: 50px;
        }

        > span {
            margin: 0px 10px;
        }

        > span:nth-child(2){
            font-size: 20px;
        }

        > div {
            margin : -5px 0px 0px auto;
        }
    }

    @media screen and (max-width: 1024px) {
        width: 100%;
        height: 80%;
    }

    @media screen and (max-width: 1255px) {
        width: 100%;
    }
`;


const View = ({ handleModal, curPost }) => {
    useEffect(() => {
        document.getElementById("bg").addEventListener("click", () => {
            handleModal();
        })
    },[handleModal])

    return (
            <Wrapper>
                <StyledSlider>
                    { curPost.postingMedias ?
                        <Slider imgs={curPost.postingMedias} type={true} /> : null
                    }
                </StyledSlider>
                <StyledInteraction>
                    <div className="profile">
                        <img src={curPost.profileImage} alt="profileImg" />
                        <span>{curPost.userName}</span>
                        <span>{curPost.modifiedAt ? exchangeTime(curPost) : ""}</span>
                        <CloseBtn handleEvent={handleModal} />
                    </div>
                    <FeedInteraction post={curPost} type={1} />
                    <Comments />
                </StyledInteraction>
            </Wrapper>
    )
}

export default View;