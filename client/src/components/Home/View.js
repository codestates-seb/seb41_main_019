import styled from "styled-components";
import { AiOutlineClose } from "react-icons/ai";
import Slider from "./Slider";
import A from "../../assets/img/plants/1.jpg";
import B from "../../assets/img/plants/알보1.png";
import FeedInteraction from "./FeedInteraction";
import Comments from "./Comments";

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

    .none {
        display: none;
    }

    svg {
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
    }
`

const StyledInteraction = styled.div`
    width: 45%;
    display: flex;
    flex-direction: column;

    > div:first-child {
        margin: 0px 0px 0px auto;
    }

    .profile {
        
        img {
            width: 40px;
            height: 40px;
            border-radius: 50px;
        }
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
            :
            null
        }
        </>
    )
}

export default View;