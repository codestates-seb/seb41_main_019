import styled from "styled-components";
import { BsFillCameraFill } from "react-icons/bs";
import { AiOutlineClose } from "react-icons/ai";

const Wrapper = styled.div`
    > p {
        margin: 0px 0px 10px 0px;
    }

    > div:nth-of-type(1) {
        display: flex;
        flex-direction: row;

    }

    input {
        display: none;
    }

    .photo {
        width: 20%;
    }

    .img {
        display: flex;
        width: 25%;
        
    }
`

const StyledDiv = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
    height: 140px;
    width: 140px;
    cursor: pointer;
    margin-right: 10px;

    :hover {
        opacity: 0.7;

        svg {
            color: black;
        }
    }

    img {
        width: 100%;
        height: 100%;
    }

    svg {
        width: 50px;
        height: 50px;
        color: gray;
        @media screen and (max-width: 770px) {
            width: 20px;   
        }
    }
`;

const StyledCancel = styled.div`
    width: 0;

    svg {
        cursor: pointer;
        position: relative;
        left: -27px;
        background-color: white;
        opacity: 0.6;

        :hover {
            scale: 1.2;
        }
    }
`

const Uploader = ({ images, handleImg, fileInputRef, deleteImg }) => {
   
    const onFileInputClick = () => {
        if(images.length < 3) {
            fileInputRef.current[images.length].click();
        }
    };

    return (
        <Wrapper>
                <p>사진이나 동영상을 등록해 주세요.(3장까지 가능합니다)</p>
                <div>
                    <StyledDiv className="photo" onClick={onFileInputClick}>
                        <BsFillCameraFill />
                    </StyledDiv>
                    { images.map((image, idx) => {
                            return (
                                <div className="img" key={idx}>
                                    <StyledDiv>
                                        <img src={image} alt="img" />
                                    </StyledDiv>
                                    <StyledCancel onClick={deleteImg}>
                                        <AiOutlineClose />
                                    </StyledCancel>
                                </div>
                            )
                        })
                    }
                </div>
                <input 
                    type="file"
                    accept="image/*,audio/*,video/mp4"
                    ref={(el) => fileInputRef.current[0] = el} 
                    onChange={handleImg} />
                <input 
                    type="file"
                    accept="image/*,audio/*,video/mp4"
                    ref={(el) => fileInputRef.current[1] = el} 
                    onChange={handleImg} />
                <input 
                    type="file"
                    accept="image/*,audio/*,video/mp4"
                    ref={(el) => fileInputRef.current[2] = el} 
                    onChange={handleImg} />
        </Wrapper>
    )
};

export default Uploader;