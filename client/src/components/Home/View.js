import styled from "styled-components";
import Slider from "./Slider";
import FeedInteraction from "./FeedInteraction";
import Comments from "./Comments";
import { useEffect } from "react";
import CloseBtn from "../public/CloseBtn";
import { exchangeTime } from "../../util/exchangeTime";
import defaultImg from "../../assets/img/profile.jpg";

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

    @media screen and (max-width: 1255px) {
        width: 900px;
        height: 900px;
    }

    @media screen and (max-width: 1024px) {
        width: 500px;
        height: 600px;
        top: 38%;
        flex-direction: column;

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
    border-left: 1px solid #DBDBDB;

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
            margin-right: 10px;
        }

        > span:nth-child(2){
            font-size: 20px;
            margin-right: 10px;
        }

        > div {
            margin : 5px 0px 0px auto;
        }
    }

    @media screen and (max-width: 1255px) {
        width: 100%;
    }

    @media screen and (max-width: 1024px) {
        width: 100%;
        height: 80%;
    }
`;

const View = ({ handleModal, curPost, handleChange, handleCommentMenu, setCommentId }) => {
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
                        <img src={curPost.profileImage ? curPost.profileImage : defaultImg} alt="profileImg" />
                        <span>{curPost.userName}</span>
                        <span>{curPost.modifiedAt ? exchangeTime(curPost) : ""}</span>
                        <CloseBtn handleEvent={handleModal} />
                    </div>
                    <FeedInteraction post={curPost} type={1} handleChange={handleChange} />
                    <Comments post={curPost} handleChange={handleChange} handleCommentMenu={handleCommentMenu} setCommentId={setCommentId} />
                </StyledInteraction>
            </Wrapper>
    )
}

export default View;