import styled from "styled-components"
import { AiFillSetting, AiOutlinePoweroff, } from "react-icons/ai";
import { BsFillJournalBookmarkFill } from "react-icons/bs";
import { useNavigate } from 'react-router-dom';
import Cookie from "../../../util/Cookie"

const StyledModal = styled.div`
  display: inline-block;
  z-index: 999;
  background-color: white;
  margin-left: 10px;
  position: fixed;
  bottom: 25px;
  width: 160px;
  height:110px;
  border-radius: 3px;
  border: 1px solid #dbdbdb;
  color: #222426;
  border-bottom: 0px;

  @media screen and (min-width: 755px) {
    left: 5px;
    right: 50px !important;
  }

  ul {
    margin: 0px;
    padding: 0px;
    width: 100%;
    list-style: none;

    li {
      display: block;
      display: flex;
      border-bottom: 1px solid #dbdbdb;
      justify-content: space-between;
      padding: 8px 5px;
      cursor: pointer;

      svg {
        font-size : 20px;
        color: #222426;
      }
    }
  }

  @media screen and (max-width: 755px) {
    width: 160px;
    right: 7% !important;
    bottom: 40px;
  }

  @media screen and (max-width: 414px) {
    width: 100px;
  }
`;

const SideModal = ({ handleOpendModal, setIsLanded }) => {
  const navigate = useNavigate();
  const cookie = new Cookie();

  return (
      <StyledModal>
        <ul>
          <li onClick={() => {
              navigate("/setting")
              handleOpendModal();
            }}>
            <span>설정</span>
            <AiFillSetting />
          </li>
          <li onClick={() => {
            navigate("/mypage")
          }}>
            <span>스크랩</span>
            <BsFillJournalBookmarkFill />
          </li>
          <li onClick={() => {
            cookie.remove("memberId");
            cookie.remove("list");
            cookie.remove("username");
            cookie.remove("refresh");
            cookie.remove("authorization");
            navigate("/");
            setIsLanded(true);
          }}>
            <span>로그아웃</span>
            <AiOutlinePoweroff />
          </li>
        </ul>
      </StyledModal>
  )
}

export default SideModal;