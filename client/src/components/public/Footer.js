import styled from "styled-components"
import { AiFillGithub, AiFillYoutube } from "react-icons/ai";
import { RxNotionLogo } from "react-icons/rx";

const Wrapper = styled.footer`
    display: flex;
	width: 100%;
	height: 110px; /* 내용물에 따라 알맞는 값 설정 */
	bottom: 0px;
	position: absolute;
    background: white;
    padding-top: 15px;
    color: #808080;
    font-size: 11px;
    justify-content: space-around;

    h1 {
        display: flex;
        justify-content: space-around;

        a {
            display: inline-block;
            color: #808080; 

            :visited {
                color: #808080;
        }
        }
    }

    .team a {
        display: inline-block;
        margin: 0 20px 10px 20px;
        color: #808080; 

        :visited {
            color: #808080;
        }
    }

    p {
        font-size: 13px;
        font-weight: 600;
        margin-top: 0; 
        margin-bottom: 2px;   
    }

    p span {
        display: inline-block;
        margin-left: 20px;
    }
`;

const Footer = () => {
    return (
        <Wrapper>
            <div>
                <h1>
                    IncleaF
                    <a href="https://www.notion.so/codestates/2d1ac8dc113646d9b74dfa27903105f2" target="_blank"><RxNotionLogo /></a>
                    <a href="https://github.com/codestates-seb/seb41_main_019" target="_blank"><AiFillGithub /></a>
                    <AiFillYoutube />
                </h1>
                <span>@ Copyright 2023. 식테크맨. All Rights Reserved.</span>
            </div>
            <nav className="team">
                <div>
                    <p>FrontEnd</p>
                    <span>이민훈</span>
                    <a href="https://github.com/lmimoh/lmimoh" target="_blank"><AiFillGithub /></a>
                    <span>강래헌</span>
                    <a href="https://github.com/kanglaeheon" target="_blank"><AiFillGithub /></a>
                    <span>장은지</span>
                    <a href="https://github.com/ohmegle" target="_blank"><AiFillGithub /></a>
                </div>
                <div>
                    <p>BackEnd</p>
                    <span>김태현</span>
                    <a href="https://github.com/taebong98" target="_blank"><AiFillGithub /></a>
                    <span>김혜인</span>
                    <a href="https://github.com/gimhae-person" target="_blank"><AiFillGithub /></a>
                    <span>한대호</span>
                    <a href="https://github.com/oheadnah" target="_blank"><AiFillGithub /></a>
                </div>
            </nav>
           
        </Wrapper>
    )
};

export default Footer;