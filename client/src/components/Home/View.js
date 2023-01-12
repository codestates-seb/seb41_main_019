import styled from "styled-components";
import { AiOutlineClose } from "react-icons/ai";
import Slider from "./Slider";
import A from "../../assets/img/plants/1.jpg";
import B from "../../assets/img/plants/알보1.png";
import FeedInteraction from "./FeedInteraction";
import { useState } from "react";

const Wrapper = styled.div`
    display: flex;
    position: fixed;
    top: 3%;
    left: 15%;
    /* transform: translate(-50%, -50%); */
    width: 1240px;
    height: 900px;
    background-color: white;
    z-index: 1000;
    border: 2px solid blue;

    .none {
        display: none;
    }

    svg {
        position: absolute;
        right: 0;
    }
`;

const StyledSlider = styled.div`
    width: 55%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    
    img {
        width: 100%;
    }
`

const StyledComments = styled.div`
    width: 45%;

    div {
        position: absolute;
        bottom: 10%;
    }
`;



const View= ({ modal, handleModal }) => {
    const img = [ A ] ;

    return (
        <>
        { 
            modal
            ?
            <Wrapper>
                <StyledSlider>
                    {  img.length > 1
                        ? <Slider img={img} /> 
                        : <img src={B} alt="img" />
                    }
                </StyledSlider>
                <StyledComments>
                    <FeedInteraction modal={modal} />
                </StyledComments>
                <AiOutlineClose onClick={() => handleModal()} />
            </Wrapper>
            :
            null
        }
        </>
    )
}

export default View;