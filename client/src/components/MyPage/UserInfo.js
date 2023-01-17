import styled from "styled-components";
import jwtDecode from "jwt-decode";
import axios from "axios";

import { AiFillSetting } from "react-icons/ai";
import { useState } from "react";
import Cookie from "../../util/Cookie";

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

const UserInfo = () => {
  const cookie = new Cookie();
  const getJWT = cookie.get("authorization");
  const decodedJWT = jwtDecode(getJWT);

  const [userInfo, setUserInfo] = useState(JSON.stringify(decodedJWT));

  const {
    memberId,
    username,
    profileImage,
    profileText,
    followingCount,
    followerCount,
  } = userInfo;

  // const postCount = axios({
  //   method: "get",
  //   url: `http://13.124.33.113:8080/posts/member/${memberId}?page=1&size=20`,
  // });

  return (
    <>
      <StyledContainer>
        <StyledUserImgWrapper>
          <img src={profileImage} alt="user profile image" />
        </StyledUserImgWrapper>
        <StyledInfoBox>
          <StyledUserName>
            <span>{username}</span>
            <a href="/setting">
              <span>
                <AiFillSetting />
                설정
              </span>
            </a>
          </StyledUserName>
          <StyledUserInfoList>
            <a href="#">
              <StyledInfoItem>
                <p>게시물</p>
                {/* 게시물 숫자 데이터 API 협의하거나 게시물 GET 요청 후 데이터 length 사용 필요 */}
                {/* <p>{postCount}</p> */}
                <p>000</p>
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
