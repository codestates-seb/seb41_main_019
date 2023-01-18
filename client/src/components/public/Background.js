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
`;

const Background = ({ isCovered }) => {
  return (
    <StyledBackground
      id="bg"
    ></StyledBackground>
  );
};

export default Background;
