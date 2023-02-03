import styled from "styled-components";

import { AiFillSetting } from "react-icons/ai";
import defaultProfileImg from "../../assets/img/plants/defaultProfileImg.png";
import { useNavigate } from "react-router-dom";
import { follow } from "../../util/follow";
import Cookie from "../../util/Cookie";

import { AiOutlineUserDelete } from "react-icons/ai"
import { AiOutlineUserAdd } from "react-icons/ai";

const StyledContainer = styled.div`
  width: 100%;
  display: flex;
  margin-top: 20px;
  justify-content: center;
  align-items: center;
  a {
    text-decoration: none;
    color: black;
  }

  > div {
    margin: 0 10px;
  }

  @media screen and (max-width: 770px) {
    width: 100%;
    font-size: 13px;
  }
`;
const StyledUserImgWrapper = styled.div`
  width: 130px;
  height: 130px;
  border-radius: 70%;
  border: solid 1px #dbdbdb;
  overflow: hidden;
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  @media screen and (max-width: 770px) {
    width: 75px;
    height: 75px;
    img {
      width: 100%;
      height: 100%;
    }
  }
`;
const StyledInfoBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 70%;
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
    cursor: pointer;
  }
`;
const StyledUserInfoList = styled.div`
  display: flex;
  justify-content: space-between;
  div {
    cursor: pointer;
  }
`;
const StyledInfoItem = styled.div`
  display: flex;
  justify-content: space-between;
  width: 80px;
`;

const StyledButton = styled.button`
border: 0px;
cursor: pointer;
background-color: white;

svg {
  font-size: 22px;
  color: #808080;

  :hover {
    color: #D96846;
  }
}
`;

const UserInfo = ({ isOwnPage, userInfo, postCount, handleFollows, handleFollowings, setCurrentView, handleChange }) => {
  const {
    memberId,
    userName,
    profileImage,
    profileText,
    followingList,
    followerList,
  } = userInfo;
  
  const cookie = new Cookie();
  const myMemberId = Number(cookie.get("memberId"));

  const navigate = useNavigate();

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
            {isOwnPage &&         
              <span onClick={() => {navigate("/setting")}}>
                <AiFillSetting />
              </span>
            }
            {!isOwnPage && followerList &&
              followerList.filter(e => e.followerId === myMemberId).length !== 0 &&
                <StyledButton onClick={(e) => {
                  e.stopPropagation();
                  follow(false, followerList.filter(e => e.followerId === myMemberId)[0].followId , handleChange)
                  }}>
                  <AiOutlineUserDelete />
                </StyledButton>
            }
            {!isOwnPage && followerList &&
              followerList.filter(e => e.followerId === myMemberId).length === 0 &&
              <StyledButton onClick={(e) => {
                e.stopPropagation();
                follow(true, memberId , handleChange)
                }}>
                < AiOutlineUserAdd />
              </StyledButton>
            }
          </StyledUserName>
          <StyledUserInfoList>
              <StyledInfoItem onClick={() => setCurrentView("postings")} >
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
