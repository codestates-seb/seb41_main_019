import styled from "styled-components";
import CloseBtn from "./CloseBtn";

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
    padding: 10px;
    border-radius: 5px;
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 300px;
    height: auto;
    background-color: white;
    position: fixed;
    top:50%;
    left:50%;
    transform:translate(-50%, -50%); 
    box-shadow: 5px 10px 10px 1px rgba(0,0,0,0.3); 
 
    >div:first-of-type {
        width: 20px;
        margin: 0 0 0 auto;
    }
`;

const Modal = ({ onClose, children, func = null }) => {
    return (
        <Wrapper onClick={() => {
            onClose()
            func && func();
        }}>
            <ModalContainer onClick={(e) => e.stopPropagation()}>
                <CloseBtn handleEvent={() => {
                    onClose()
                    func && func();
                }} />
                {children}
            </ModalContainer>
        </Wrapper>
    )
};

export default Modal;