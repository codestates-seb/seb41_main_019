import styled from "styled-components";

import { AiFillSetting } from "react-icons/ai";
import defaultProfileImg from "../../assets/img/plants/defaultProfileImg.png";

const StyledContainer = styled.div`
  display: flex;
  margin-top: 20px;
  justify-content: center;
  a {
    text-decoration: none;
    color: black;
  }

  > div {
    margin: 0 10px;
  }
`;
const StyledUserImgWrapper = styled.div`
  width: 150px;
  height: 150px;
  border-radius: 70%;
  border: solid 1px #dbdbdb;
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
  span {
    display: flex;
    align-items: center;
  }
`;
const StyledUserInfoList = styled.div`
  display: flex;
  justify-content: space-between;
  div:nth-child(2), div:nth-child(3) {
    cursor: pointer;
  }
`;
const StyledInfoItem = styled.div`
  display: flex;
  justify-content: space-between;
  width: 80px;
`;

const UserInfo = ({ userInfo, postCount, handleFollows, handleFollowings }) => {
  const {
    memberId,
    userName,
    email,
    location,
    profileImage,
    profileText,
    followingList,
    followerList,
  } = userInfo;

  return (
    <>
      <StyledContainer>
        <StyledUserImgWrapper>
          <img
            src={profileImage ? profileImage : defaultProfileImg}
            alt="user profile"
          />
        </StyledUserImgWrapper>
        <StyledInfoBox>
          <StyledUserName>
            <span>{userName}</span>
            <a href="/setting">
              <span>
                <AiFillSetting />
                설정
              </span>
            </a>
          </StyledUserName>
          <StyledUserInfoList>
              <StyledInfoItem>
                <p>게시물</p>
                <p>{postCount}</p>
              </StyledInfoItem>
              <StyledInfoItem onClick={handleFollows}>
                <p>팔로워</p>
                <p>{followerList ? followerList.length : 0}</p>
              </StyledInfoItem>
              <StyledInfoItem onClick={handleFollowings}>
                <p>팔로잉</p>
                <p>{followingList ? followingList.length : 0}</p>
              </StyledInfoItem>
          </StyledUserInfoList>
          <div>
            <p>{profileText ? profileText : "..."}</p>
          </div>
        </StyledInfoBox>
      </StyledContainer>
    </>
  );
};

export default UserInfo;
