import styled from "styled-components"

import { BsFillCameraFill } from "react-icons/bs";
import { BlueBtn } from "../public/BlueBtn"

const StyledContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: absolute;
    background-color: white;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
    box-shadow: 5px 5px 10px 1px rgba(0, 0, 0, 0.3);
    width: 220px;
    height: 240px;
    top: 40px;
    right: -220px;
    padding: 20px;
    .button-container {
        display: flex;
        width: 220px;
        transform: translate(20px, 10px);
        > button:last-of-type {
            background-color: #d96848;
        }
    }
`

const StyledInputWrapper = styled.div`
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

const AddPlantImage = ({handleAddClick}) => {
    return (
        <StyledContainer>
            <StyledInputWrapper>
                <BsFillCameraFill />
            </StyledInputWrapper>
            <div className="button-container">
                <BlueBtn type="submit">완료</BlueBtn>
                <BlueBtn onClick={handleAddClick}>취소</BlueBtn>
            </div>
        </StyledContainer>
    )
}

export default AddPlantImage