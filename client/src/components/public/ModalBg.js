import styled from "styled-components";

const StyledBackground = styled.div`
    width: 100%;
    height: 100%;
    position: fixed;
    top:0;
    left:0;
    right:0;
    background: rgba(0,0,0,0.6);
    z-index: 999;
`


const ModalBg = () => {
    return (
        <StyledBackground></StyledBackground>
    );
}

export default ModalBg;