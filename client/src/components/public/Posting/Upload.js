import styled from "styled-components";
import { BsFillCameraFill } from "react-icons/bs";
import { AiOutlineClose } from "react-icons/ai";
import React, { useRef, useState } from "react";

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
`

const StyledDiv = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
    width: 150px;
    height: 150px;
    cursor: pointer;
    margin-right: 10px;

    :hover {
        background-color: gray;
        opacity: 0.2;

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
    }
`;

const StyledCancel = styled.div`
    width: 0;

    svg {
        cursor: pointer;
        position: relative;
        left: -27px;
        background-color: white;

        :hover {
            scale: 1.2;
        }
    }
`

const Upload = () => {
    const fileInputRef = useRef();
    const [images, setImages] = useState([]);

    const onFileInputClick = (e) => {
        fileInputRef.current.click();
    };

    const handleDelete = (index) => {
       setImages(images.slice())
    };

    return (
        <Wrapper>
                <p>사진이나 동영상을 등록해 주세요.(3장까지 가능합니다)</p>
                <div>
                    <StyledDiv onClick={onFileInputClick}>
                        <BsFillCameraFill />
                    </StyledDiv>
                    { images.map((image, idx) => {
                            return (
                                <>
                                    <StyledDiv key={idx}>
                                        <img src={image} alt="img" />
                                    </StyledDiv>
                                    <StyledCancel>
                                        <AiOutlineClose onClick={handleDelete} />
                                    </StyledCancel>
                                </>
                            )
                        })
                    }
                </div>
                <input 
                    type="file"
                    ref={fileInputRef} 
                    onChange={(e) => {
                        const file = fileInputRef.current.files[0];
	                    const reader = new FileReader();
                        reader.readAsDataURL(file);
                        reader.onloadend = () => {
                            setImages([...images, reader.result]);
                        };
                    }} />
        </Wrapper>
    )
};

export default Upload;