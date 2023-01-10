import styled from "styled-components";
import { RiLeafLine } from "react-icons/ri";
import { AiOutlineHome } from "react-icons/ai";
import { BsSearch } from "react-icons/bs";
import { AiOutlineMessage } from "react-icons/ai";
import { IoAlertCircleOutline } from "react-icons/io5";
import { BsPerson } from "react-icons/bs";
import { AiOutlineMenu } from "react-icons/ai";
import { AiFillSetting } from "react-icons/ai";
import { useState } from "react";
import { BsFillJournalBookmarkFill } from "react-icons/bs";
import { AiOutlineClockCircle } from "react-icons/ai";
import { FaExchangeAlt } from "react-icons/fa";
import { AiOutlinePoweroff } from "react-icons/ai";

const StyledSidebar = styled.aside`
    z-index: 100;
    display: flex;
    box-sizing: border-box;
    padding: 0px 0px 0px 15px;
    flex-direction: column;
    justify-content: space-between;
    position: fixed;
    height: 100%;
    width: 350px;
    border-right: 1px solid #DBDBDB;
    box-shadow: 1px 1px 1px 1px #DBDBDB;

    h2 {
        font-weight: 400;
    }

    nav h2 svg {
        color: #0FA958;
        font-size: 33px;
    }

    ul {
        list-style: none;
        margin: 0px;
        padding: 0px;
        
        li {
            margin-bottom: 20px;

            svg {
                margin-right: 8px;
            }
        }
    }

    p span {
        margin-left: 10px;
    }

    @media screen and (max-width: 1255px) {
        width: 60px;

        ul li span {
            display: none;
        }

        nav h2 span {
            display: none;
        }

        p span {
            display: none;
        }
    }


    @media screen and (max-width: 770px) {
        align-items: center;
        argin: 0 auto;
        flex-direction: row;
        justify-content: space-evenly;
        position: fixed;
        padding: 0px;
        top: 92%;
        width: 100%;
        height: 60px;
        border: 1px solid #DBDBDB;
        box-shadow: 0px;
        
        h2, p {
            display: none;
        }

        nav ul {
            display: flex;
            flex: 1 0 auto;

            li {
                margin-right: 50px;
            }

            ul>li:nth-child(2) {
                display: none;
            }
        }
    }
`;

const Modal = styled.div`
    display: inline-block;
    z-index: 400;
    background-color: white;
    margin-left: 10px;
    position: relative;
    top: 67vh;
    width: 200px;
    height: 180px;
    border-radius: 20px;
    border: 1px solid gray;

    ul {
        padding: 9px;
        list-style: none;

        li {
            display: block;
            display: flex;
            justify-content: space-between;
            padding: 5px;
        }
    }

    @media screen and (max-width: 770px) {
        display: none;
    }
`;

const SearchBar = styled.div`
    width: 100%;
    height: 60px;
    position: fixed;
    top: 0;
    display: flex;
    align-items: center;
    justify-content: space-around;
    border-bottom: 1px solid #DBDBDB;

    h4 svg {
        color: #0FA958;
        font-size: 25px;
    }

    div {
        display: flex;
        position: relative;

        svg {
            position: absolute;
            width: 17px;
            top: 10px;
            right: 137px;
            margin: 0;
        }
    }

    input {
        width: 100%;
        height: 35px;
        border-radius: 10px;
        border: 1px solid #DBDBDB;
        padding: 10px 12px;
    }

    @media screen and (min-width: 755px) { 
        display: none; 
    }
`;

const Sidebar = () => {
    const [ modalView, setModalView ] = useState(false);

    const openModal = () => {
        setModalView(!modalView);
    }

    return (
        <>
            <SearchBar>
                <h4><span>IncleaF</span><RiLeafLine /></h4>
                <div>
                    <BsSearch />
                    <input></input>
                </div>
            </SearchBar>
            <StyledSidebar>
                <nav>
                    <h2><span>IncleaF</span><RiLeafLine /></h2>
                    <ul>
                        <li><AiOutlineHome /> <span>홈</span></li>
                        <li><BsSearch /><span> 검색</span></li>
                        <li><AiOutlineMessage /> <span>채팅</span></li>
                        <li><IoAlertCircleOutline /> <span>알림</span></li>
                        <li><BsPerson /> <span>프로필</span></li>
                    </ul>
                </nav>
                <p onClick={openModal}><AiOutlineMenu /><span>더 보기</span></p>    
            </StyledSidebar>
            {
                modalView 
                ? <Modal>
                    <ul>
                        <li><span>설정</span><AiFillSetting /></li>
                        <li><span>스크랩</span><BsFillJournalBookmarkFill /></li>
                        <li><span>내 활동</span><AiOutlineClockCircle /></li>
                        <li><span>계정 전환</span><FaExchangeAlt /></li>
                        <li><span>로그아웃</span><AiOutlinePoweroff /></li>
                    </ul>
                </Modal>
                : null
            }
        </>
    )
}

export default Sidebar;

/*
    사이드바를 사용시 레이아웃을 정렬해주는 css입니다
*/

export const Container = styled.div`
    position: relative;
    max-width: 1264px; // 최대 너비를 지정  -> 이 값 이상으로는 안커짐
    width: 100%; // 크기 100% 설정 ->위의 max-width와 조합하여 1264픽셀 이하에서 자동으로 크기가 줄었다 늘었다 함
    flex: 1 0 auto;
    display: flex;
    justify-content: space-between;
    margin: 0 auto; // 증앙정렬
`;

// 컨텐츠가 들어올 영역입니다
export const Main = styled.main`
    max-width: 1100px; // 최대 너비를 지정  -> 이 값 이상으로는 안커짐
    width: calc(100% - 200px); // css 함수 너비 100%에서 164px만큼 크기를 줄임(여백을 위해) https://developer.mozilla.org/ko/docs/Web/CSS/calc
    padding: 24px;
    z-index: 100;
    @media screen and (max-width: 980px) {
        padding-left: 16px;
        padding-right: 16px;
    }
    @media screen and (max-width: 640px) {
        width: 100%;
        border-left: 0;
        border-right: 0;
    }
`;