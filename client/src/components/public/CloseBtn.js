import { AiOutlineClose } from "react-icons/ai";
import styled from "styled-components";

const StyledBtn = styled.div`
    font-size: 20px;
    cursor: pointer;

    :hover {
        transform: scale(1.2);
    }
`;

const CloseBtn = ({ handleModal }) => {
    return (
        <StyledBtn>
            <AiOutlineClose onClick={() => handleModal()} />
        </StyledBtn>
    )
};

export default CloseBtn;