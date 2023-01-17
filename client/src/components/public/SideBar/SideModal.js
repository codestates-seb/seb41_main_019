import styled from "styled-components"
import { AiFillSetting, AiOutlineClockCircle, AiOutlinePoweroff, } from "react-icons/ai";
import { BsFillJournalBookmarkFill } from "react-icons/bs";
import { FaExchangeAlt } from "react-icons/fa";
import { useNavigate } from 'react-router-dom';

const StyledModal = styled.div`
  display: inline-block;
  z-index: 999;
  background-color: white;
  margin-left: 10px;
  position: absolute;
  left: 5px;
  bottom: 25px;
  width: 210px;
  border-radius: 3px;
  border: 1px solid #dbdbdb;
  color: #222426;
  border-bottom: 0px;

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

  @media screen and (max-width: 770px) {
    display: none;
  }
`;

const SideModal = ({ handleOpendModal }) => {
  const navigate = useNavigate();

    return (
        <StyledModal>
          <ul>
            <li>
              <span onClick={() => {
                navigate("/setting")
                handleOpendModal();
              }}>설정</span>
              <AiFillSetting />
            </li>
            <li>
              <span>스크랩</span>
              <BsFillJournalBookmarkFill />
            </li>
            <li>
              <span>내 활동</span>
              <AiOutlineClockCircle />
            </li>
            <li>
              <span>로그아웃</span>
              <AiOutlinePoweroff />
            </li>
          </ul>
        </StyledModal>
    )
}

export default SideModal;