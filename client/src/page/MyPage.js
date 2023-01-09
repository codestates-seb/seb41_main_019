import styled from "styled-components";

import MyPlants from "../components/MyPage/MyPlants";
import UserInfo from "../components/MyPage/UserInfo";

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 750px;
`;

const MyPage = () => {
  return (
    <StyledContainer>
      <UserInfo />
      <MyPlants />
    </StyledContainer>
  );
};

export default MyPage;
