import styled from "styled-components";
import { AiOutlineClose } from "react-icons/ai";
import Slider from "./Slider";
import A from "../../assets/img/plants/1.jpg";
import B from "../../assets/img/plants/알보1.png";
import FeedInteraction from "./FeedInteraction";
import Comments from "./Comments";
import { useEffect } from "react";

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
        height: 600px;
        top: 40%;
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
    width: 55%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    background-color: black;

    img {
        width: 100%;

        @media screen and (max-width: 1024px) {
            width: 70%;
            height: 100%;
            
        }
    }

    @media screen and (max-width: 1255px) {
        width: 100%;
    }

    @media screen and (max-width: 1024px) { 
        align-items: center;
    }
`

const StyledInteraction = styled.div`
    width: 45%;
    display: flex;
    flex-direction: column;

    > div:first-child {
        margin: 0px 0px 0px auto;

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
    }

    @media screen and (max-width: 1024px) {
        width: 100%;
        height: 80%;
    }

    @media screen and (max-width: 1255px) {
        width: 100%;
    }
`;


const View= ({ handleModal }) => {
    const img = [ A ] ;

    useEffect(() => {
        document.getElementById("bg").addEventListener("click", () => {
            handleModal();
        })
    },[handleModal])

    return (
            <Wrapper>
                <StyledSlider>
                    {  img.length > 1
                        ? <Slider img={img} /> 
                        : <img src={B} alt="img" />
                    }
                </StyledSlider>
                <StyledInteraction>
                    <div>
                        <AiOutlineClose onClick={() => handleModal()} />
                    </div>
                    <div className="profile">
                        <img src={A} alt="profileImg" />
                        <span>홍길동</span>
                        <span>7시간 전</span>
                    </div>
                    <FeedInteraction type={1} />
                    <Comments />
                </StyledInteraction>
            </Wrapper>
    )
}

export default View;