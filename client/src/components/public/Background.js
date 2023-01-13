import { useEffect } from "react";
import styled from "styled-components";

const StyledBackground = styled.div`
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  background: rgba(0, 0, 0, 0.6);
  z-index: 999;
  display: ${({ isCovered }) => (isCovered ? null : "none")};
`;

const Background = ({ isCovered, handleIsCovered }) => {
  useEffect(() => {
    document.body.style.overflow = isCovered ? "hidden" : "auto";
  }, [isCovered]);

  return (
    <StyledBackground
      isCovered={isCovered}
      onClick={handleIsCovered}
    ></StyledBackground>
  );
};

export default Background;
