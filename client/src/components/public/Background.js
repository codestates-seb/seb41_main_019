import styled from "styled-components";

const StyledBackground = styled.div`
    width: 1980px;
    height: 1024px;
    position: fixed;
    top:0;
    left:0;
    background: rgba(0,0,0,0.6);
    z-index: 999;
    display: ${({isCovered}) => isCovered ? null : "none"};
`

const Background = ({ isCovered }) => {
    return (
        <StyledBackground isCovered={isCovered}></StyledBackground>
    );
};

export default Background;