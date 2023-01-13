import styled from "styled-components";
import { AiFillSetting } from "react-icons/ai";

const StyledContainer = styled.div`
  display: flex;
  align-items: initial;
  svg {
    width: 20px;
    height: 20px;
  }
`;

const MyPlantSettings = () => {
  return (
    <StyledContainer>
      <AiFillSetting />
    </StyledContainer>
  );
};

export default MyPlantSettings;
