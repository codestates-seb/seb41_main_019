import styled from "styled-components";
import axios from "axios";

import { AiFillSetting } from "react-icons/ai";
import { useState, useEffect } from "react";
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
`;
const StyledInfoItem = styled.div`
  display: flex;
  justify-content: space-between;
  width: 80px;
`;

const UserInfo = ({ userInfo, jwt }) => {
  const [postCount, setPostCount] = useState();
  const {
    memberId,
    username,
    profileImage,
    profileText,
    followingCount,
    followerCount,
  } = userInfo;

  useEffect(() => {
    getPostCount();
  }, []);

  const getPostCount = async () => {
    axios({
      method: "get",
      url: `http://13.124.33.113:8080/posts/members/${memberId}?page=1&size=20`,
      headers: {
        Authorization: jwt,
      },
    }).then((res) => {
      const data = JSON.stringify(res.data);
      const JSONdata = JSON.parse(data);
      setPostCount(JSONdata.pageInfo.totalElements);
    });
  };

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
                <p>{postCount}</p>
              </StyledInfoItem>
            </a>
            <a href="#">
              <StyledInfoItem>
                <p>팔로워</p>
                <p>{followerCount ? followerCount : 0}</p>
              </StyledInfoItem>
            </a>
            <a href="#">
              <StyledInfoItem>
                <p>팔로잉</p>
                <p>{followingCount ? followingCount : 0}</p>
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
