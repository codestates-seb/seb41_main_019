import styled from "styled-components";
import { BsFillCameraFill } from "react-icons/bs";
import { AiOutlineClose } from "react-icons/ai";
import React, { useRef, useState } from "react";

const Wrapper = styled.div`
    > div:nth-of-type(1) {
        display: flex;
        flex-direction: row;
    }

    >div > svg {
        position: relative;
        left : -15px;
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

    img {
        width: 100%;
        height: 100%;
    }
    svg {
        width: 50px;
        height: 50px;
    }
`;

const StyledCancel = styled.div`
    width : 0px;
    height : 0px;

    svg {
        position: relative;
        left: -15px;
        background-color: white;
    }
`

const Upload = () => {
    const fileInputRef = useRef();
    const [images, setImages] = useState([]);

    const onFileInputClick = (e) => {
        fileInputRef.current.click();
        // console.log(fileInputRef)
    };

    return (
        <Wrapper>
                <p>사진이나 동영상을 등록해 주세요.</p>
                <div>
                    <StyledDiv onClick={onFileInputClick}>
                        <BsFillCameraFill />
                    </StyledDiv>
                    {
                        images.map((image, idx) => {
                            return (
                            <>
                                <StyledDiv key={idx}>
                                    <img src={image} alt="img" />
                                </StyledDiv>
                                <StyledCancel>
                                    <AiOutlineClose />
                                </StyledCancel>
                            </>)
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