import styled from "styled-components";

const StyledBackground = styled.div`
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  background: rgba(0, 0, 0, 0.6);
  z-index: 999;
  display: block;
  animation: modal-bg-show 0.3s;

  @keyframes modal-bg-show {
    from {
      opacity: 0;
    }
    to {
      opacity: 1;
    }
  }
`;

const Background = ({ isCovered }) => {
  return (
    <StyledBackground
      id="bg"
    ></StyledBackground>
  );
};

export default Background;
