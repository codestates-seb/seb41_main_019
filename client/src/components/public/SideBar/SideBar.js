import styled from "styled-components";
import { AiOutlineHome, AiOutlineMessage, AiOutlineMenu } from "react-icons/ai";
import { BsSearch, BsPerson, BsPlusSquareDotted } from "react-icons/bs";
import { IoAlertCircleOutline } from "react-icons/io5";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import SideModal from "./SideModal";
import Search from "../Search/Search";
import Chat from "../Chat/Chat";
import { useLocation } from "react-router-dom";
import Alert from "../Alert/Alert";
import Cookie from "../../../util/Cookie";
import icon from "../../../assets/icon.png";
import logo from "../../../assets/logo.png";
import useModal from "../../../hooks/useModal";

const StyledSidebar = styled.aside`
  z-index: 600;
  display: flex;
  box-sizing: border-box;
  padding: 0px 0px 0px 15px;
  flex-direction: column;
  justify-content: space-between;
  position: fixed;
  height: 100%;
  width: 270px;
  border-right: 1px solid #dbdbdb;
  color: #222426;
  background-color: white;
  transition: width 0.2s ease;

  h2 {
    display: flex;
    align-items: flex-end;
    font-weight: 400;
    letter-spacing: 2px;
    cursor : pointer;

    .small {
      display: none;
    }
  }

  h2 img {
    height: 50px;
  }

  nav ul {
    list-style: none;
    padding: 0px;
    margin: 0px;
    display: inline-flex;
    flex-direction: column;
    height: 250px;
    justify-content: space-between;

    li {
      display: flex;
      align-items: flex-end;
      cursor: pointer;
    }

    li svg {
      font-size: 22px;
      margin-right: 10px;
    }
  }

  > div {
    display: inline-block;
    display: flex;
    align-items: flex-end;
    margin-bottom: 10px;
    cursor: pointer;

    span {
      margin-left: 10px;
    }

    svg {
      font-size: 22px;
    }
  }

  ${({ isOpend }) => isOpend ? 
  `@media screen and (min-width: 1255px) {
    width: 60px; 

    nav ul li span {
      display: none;
    }

    nav h2 span {
      display: none;
    }

    > div > span {
      display: none;
    }

    transition: width 0.2s ease;

    .big {
      display: none;
    }

    .small {
      display: block !important;
      height: 25px;
    }
  }` : null}

  @media screen and (max-width: 1255px) {
    width: 60px;

    nav ul li span {
      display: none;
    }

    nav h2 span {
      display: none;
    }

    > div > span {
      display: none;
    }

    .big {
      display: none;
    }

    .small {
      display: block !important;
      height: 25px;
    }
  }

  @media screen and (max-width: 755px) {
    align-items: center;
    flex-direction: row;
    justify-content: space-evenly;
    position: fixed;
    padding: 0px;
    bottom: 0px;
    width: 100%;
    height: 60px;
    border: 1px solid #dbdbdb;
    box-shadow: 0px;

    h2 {
      display: none;
    }

    nav {
      width: 100%;
    }

    nav ul {
      flex-direction: row;
      justify-content: space-around;
      height: 100%;
      width: 100%;
    }

    .none {
      display: none;
    }
  }

  @media screen and (min-width: 755px) {
    .hambuger {
      position: fixed;
      bottom: 10px;
    }
  }
`;

/* 770px일 때 상단 부분입니다. */
const StyledHeader = styled.header`
  z-index: 501;
  width: 100%;
  height: 60px;
  position: fixed;
  top: 0;
  display: none;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #dbdbdb;
  padding: 0px 20px;
  min-width: 355px;
  background-color: white;

  h3 {
    display: flex;
    align-items: flex-end;
    font-weight: 400;
    letter-spacing: 2px;
    cursor: pointer;
  }

  h3 img {
    height: 35px;
  }

  div {
    display: flex;

    svg {
      position: relative;
      width: 17px;
      top: 10px;
      right: -25px;
    }
  }

  input {
    width: 100%;
    height: 35px;
    border-radius: 10px;
    border: 1px solid #dbdbdb;
    padding: 10px 12px 10px 30px;
    outline:none;
    display: flex;

    :focus {
      box-shadow: 0 0 6px #5e8b7e;
    }
  }

  @media screen and (max-width: 755px) {
    display: flex;
  }
`;

const StyledExtend = styled.div`
  @media screen and (max-width: 755px) {
    > div {
      top: 60px !important;
      left: 0px !important;
    }
  }

  > div {
    z-index: 500;
    position: fixed;
    top: 0px;
    left: 60px;
    width: 0px;
    height: 100%;
    transition: width 0.2s linear;
    overflow: hidden;
    background-color: white;
    box-shadow: 1px 0px 5px gray;
  }

  > div:first-of-type {
    @media screen and (max-width: 755px) {
      display: none;
    }
  }

  .active {
    width: 350px;
    border-right: 1px solid #DBDBDB; 

    @media screen and (max-width: 755px) { 
      height: calc(100% - 120px) !important;
      top: 60px;
      left: 0px;
    }
  }
`

const Sidebar = ({ handleIsPosted, setIsLanded, change, setChange }) => {
  const [opendModal, setOpendModal] = useState(false);
  const [isOpend, setIsOpend] = useState();
  const navigate = useNavigate();
  const location = useLocation();
  const { open, Modal } = useModal();
  const id = new Cookie().get("memberId");

  useEffect(() => {
    if(location.pathname === "/landing") {
      setIsLanded(true);
    }
  }, [location, setIsLanded])

  const handleIsOpend = (value) => {
    if(value === isOpend) setIsOpend(null);   
    else setIsOpend(value);
  }

  const handleOpendModal = () => {
    setOpendModal(!opendModal);
  };

  return (
    <>
      <StyledHeader>
        <h3 onClick={() => navigate("/")}>
          <img src={logo} alt="img" />
        </h3>
        <div>
          <BsSearch />
          <input type="text" placeholder="Search..." onKeyDown={(e) => {
            if(e.key === "Enter") {
              open();
              e.target.value = "";
            }
          }}></input>
        </div>
      </StyledHeader>
      <StyledSidebar isOpend={isOpend} className="isOpend">
        <nav>
          <h2 onClick={() =>{
            handleIsOpend();
            navigate("/")
          }}>
            <img src={logo} alt="img" className="big" />
            <img src={icon} alt="img" className="small" />
          </h2>
          <ul>
            <li onClick={() =>{
            handleIsOpend();
            navigate("/")
          }}>
              <AiOutlineHome /> <span>홈</span>
            </li>
            <li className="none" onClick={() => handleIsOpend("Search")}>
              <BsSearch />
              <span> 검색</span>
            </li>
            <li onClick={() => handleIsOpend("Chat")}>
              <AiOutlineMessage /> <span>채팅</span>
            </li>
            <li onClick={() => handleIsOpend("Alert")}>
              <IoAlertCircleOutline /> <span>알림</span>
            </li>
            <li onClick={() => {
              handleIsOpend();
              navigate("/mypage", {state:{id}})
            }}>
              <BsPerson /> <span>프로필</span>
            </li>
            <li onClick={handleIsPosted}>
              <BsPlusSquareDotted /> <span>작성하기</span>
            </li>
            <li className="hambuger" onClick={handleOpendModal}>
              <AiOutlineMenu /> <span>더 보기</span>
            </li>
          </ul>
        </nav>
        {
          opendModal ? <SideModal handleOpendModal={handleOpendModal} setIsLanded={setIsLanded} /> : null
        }
        {/* <div className="hambuger" onClick={handleOpendModal}>
          <AiOutlineMenu />
          <span>더 보기</span>
        </div> */}
      </StyledSidebar>
      <StyledExtend>
        <div className={isOpend === "Search" ? "active" : null}>
          { isOpend === "Search" && <Search open={open}/> }
        </div>
        <div className={isOpend === "Chat" ? "active" : null}>
          <Chat change={change}/>
        </div>
        <div className={isOpend === "Alert" ? "active" : null}>
          <Alert />
        </div>
      </StyledExtend>
      <Modal>
        <p>현재 검색 기능은 구현중입니다.</p>
      </Modal>
    </>
  );
};

export default Sidebar;
