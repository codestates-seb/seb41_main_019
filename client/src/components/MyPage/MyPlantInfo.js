import styled from "styled-components";
import MyPlantSettings from "./MyPlantSettings";

const StyledContainer = styled.div`
  display: flex;
`;

const StyledPlantInfoBox = styled.div``;

const MyPlantInfo = () => {
  return (
    <StyledContainer>
      <StyledPlantInfoBox>
        <div>식물이름</div>
        <div>
          <span>동충하초</span>
          <span>2년 5개월</span>
        </div>
      </StyledPlantInfoBox>
      <MyPlantSettings />
    </StyledContainer>
  );
};

export default MyPlantInfo;
