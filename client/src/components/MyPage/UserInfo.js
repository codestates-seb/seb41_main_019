import styled from "styled-components";
import { AiFillSetting } from "react-icons/ai";
import { useState } from "react";

const StyledContainer = styled.div`
  display: flex;
  margin-top: 20px;
  justify-content: center;
  > div {
    margin: 0 10px;
  }
`;
const StyledUserImgWrapper = styled.div`
  width: 150px;
  height: 150px;
  border-radius: 70%;
  overflow: hidden;
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
`;
const StyledInfoBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 320px;
  height: 150px;
  > div {
    height: 50px;
    align-items: center;
  }
`;
const StyledUserName = styled.div`
  display: flex;
  justify-content: space-between;
  span:first-child {
    font-size: 20px;
  }
  span {
    display: flex;
    align-items: center;
  }
`;
const StyledUserInfoList = styled.div`
  display: flex;
  justify-content: space-between;
`;
const StyledInfoItem = styled.div`
  display: flex;
  justify-content: space-between;
  width: 80px;
`;

// 더미데이터, 추후 API 완성시 GET 요청으로 받아올 것
const dummyData = {
  memberId: 10,
  userName: "USER",
  location: "Seoul",
  profileImage:
    "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
  profileText: "방어먹고싶다",
  followingCount: 39,
  followerCount: 123,
};

const UserInfo = () => {
  const [userInfo, setUserInfo] = useState(dummyData);
  const { userName, profileImage, profileText, followingCount, followerCount } =
    userInfo;

  const handleSettingClick = () => {
    // 세팅 모달 활성화 로직
  };

  return (
    <>
      <StyledContainer>
        <StyledUserImgWrapper>
          <img src={profileImage} alt="user profile image" />
        </StyledUserImgWrapper>
        <StyledInfoBox>
          <StyledUserName>
            <span>{userName}</span>
            <span onClick={handleSettingClick}>
              <AiFillSetting />
              설정
            </span>
          </StyledUserName>
          <StyledUserInfoList>
            <a href="#">
              <StyledInfoItem>
                <p>게시물</p>
                {/* 게시물 숫자 데이터 API 협의하거나 게시물 GET 요청 후 데이터 length 사용 필요 */}
                <p>10</p>
              </StyledInfoItem>
            </a>
            <a href="#">
              <StyledInfoItem>
                <p>팔로워</p>
                <p>{followerCount}</p>
              </StyledInfoItem>
            </a>
            <a href="#">
              <StyledInfoItem>
                <p>팔로잉</p>
                <p>{followingCount}</p>
              </StyledInfoItem>
            </a>
          </StyledUserInfoList>
          <div>
            <p>{profileText}</p>
          </div>
        </StyledInfoBox>
      </StyledContainer>
    </>
  );
};

export default UserInfo;
