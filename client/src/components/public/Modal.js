import styled from "styled-components";

const Wrapper = styled.div`
    width: 100%;
    height: 100%;
    overflow: hidden;
    position: fixed;
    top: 0;
    left: 0;
    background-color: rgba(0,0,0,0.3);
    z-index: 1000;
`;

const ModalContainer = styled.div`
    width: 380px;
    padding: 20px;
    background-color: white;
    margin: 0 auto;
    top: 50%;
    left: 50%;
    transform:translate(-50%, 500%);
`;

const Modal = ({ onClose }) => {
    return (
        <Wrapper onClick={onClose}>
            <ModalContainer onClick={(e) => e.stopPropagation()}>
                dfdf
            </ModalContainer>
        </Wrapper>
    )
};

export default Modal;