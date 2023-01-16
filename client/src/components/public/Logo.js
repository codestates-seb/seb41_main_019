import styled from "styled-components";
import { RiLeafLine } from "react-icons/ri";

const StyledLogo = styled.h2`
    display: flex;
    align-items: flex-end;
    font-weight: 400;
    letter-spacing: 2px;
    cursor: pointer;

    svg {
        color: #5e8b7e;
        font-size: 25px;
        margin: 0px 0px 3px -2px;
    }
`

const Logo = () => {
    return (
        <StyledLogo>
            <span>IncleaF</span>
            <RiLeafLine />
        </StyledLogo>
    )
}

export default Logo;