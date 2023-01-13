import styled from "styled-components";
import { BsFillCameraFill } from "react-icons/bs";

const StyledDiv = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
    width: 150px;
    height: 150px;
    cursor: pointer;

    svg {
        width: 50px;
        height: 50px;
    }
`;

const Upload = () => {
    return (
        <>
            <div>
                <p>사진이나 동영상을 등록해 주세요.</p>
                <StyledDiv>
                    <BsFillCameraFill />
                </StyledDiv>
            </div>
        </>
    )
};

export default Upload;