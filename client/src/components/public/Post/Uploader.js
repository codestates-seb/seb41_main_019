import styled from "styled-components";
import { BsFillCameraFill } from "react-icons/bs";

const Wrapper = styled.div`
    > p {
        margin: 0px 0px 10px 0px;
    }

    > div:nth-of-type(1) {
        display: flex;
        flex-direction: row;
    }

    .photo {
        width: 20%;
        margin-right: 10px;
    }

    .img {
        display: flex;
        width: 150px; 
        @media screen and (max-width: 1255px) {
            width: 150px;
        }      
    }

    input {
        display: none;
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

    @media screen and (max-width: 770px) {
        width: 100px;
        height: 100px;
    }
`;

const StyledCancel = styled.button`
    position: relative;
    left: -19px;
    width: 20px;
    height: 20px;
    background-color: white !important;
    border: 0px;
    cursor: pointer;
`

const Uploader = ({ images, handleImg, deleteImg, fileInputs, edit }) => {
    const onFileInputClick = () => {
        if(images.length < 3) {
            const input = document.createElement("input");
            input.type = "file";
            input.accept = "image/*,audio/*,video/mp4";
            input.onchange = handleImg;
            fileInputs.current.append(input);
            fileInputs.current.lastChild.click();
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
                                        <img src={edit ? image.mediaUrl : image} alt="img" />
                                    </StyledDiv>
                                    <StyledCancel id={idx} onClick={deleteImg}>x</StyledCancel>
                                </div>
                            )
                        })
                    }
                </div>
                <div ref={fileInputs}>
                    {
                        edit ?
                            images.map((el, idx) => el.mediaId > 0 ? <input type="file" key={idx} hidden /> : null) : null
                    }
                </div>
        </Wrapper>
    )
};

export default Uploader;