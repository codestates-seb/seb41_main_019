import { VscClose } from "react-icons/vsc";
import styled from "styled-components";

const StyledBtn = styled.div`
    font-size: 20px;
    cursor: pointer;

    :hover {
        transform: scale(1.2);
    }
`;

const CloseBtn = ({ handleEvent }) => {
    return (
        <StyledBtn>
            <VscClose onClick={handleEvent} />
        </StyledBtn>
    )
};

export default CloseBtn;