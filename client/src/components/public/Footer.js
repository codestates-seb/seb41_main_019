import styled from "styled-components"
import { AiFillGithub, AiFillYoutube } from "react-icons/ai";
import { RxNotionLogo } from "react-icons/rx";
import { FaMicroblog } from "react-icons/fa";

const Wrapper = styled.footer`
    display: flex;
    width: 100%;
    height: 150px; /* 내용물에 따라 알맞는 값 설정 */
    bottom: 0;
    position: fixed;
    background: white;
    padding-top: 15px;
    color: #808080;
    font-size: 11px;
    justify-content: center;

    a {
        margin: 0;
    }

    > div:first-child {
        display: flex;
        flex-direction: column;
        margin-right: 80px;

        div {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        a {
            display: inline-block;
            color: #808080; 
            font-size: 20px;

            :visited {
                color: #808080;
            }
        }   
    }

    nav div {
        display: flex;
        width: 400px;
        gap: 10px;
    }

    nav a {
        display: inline-block;
        color: #808080; 

        :visited {
            color: #808080;
        }
    }

    nav svg {
        font-size: 15px;
    }

    nav span {
        font-size: 15px;
    }

    p {
        font-size: 13px;
        font-weight: 600;
        margin: 5px 0px 3px 0px;   
    }

    @media screen and (max-width: 770px) {
        display: none;
    }
`;

const Footer = () => {
    return (
        <Wrapper>
            <div>
                <div>
                    <h1>IncreaF</h1>
                    <div>
                        <a href="https://www.notion.so/codestates/2d1ac8dc113646d9b74dfa27903105f2" target="_blank" rel="noopener noreferrer"><RxNotionLogo /></a>
                        <a href="https://github.com/codestates-seb/seb41_main_019" target="_blank" rel="noopener noreferrer"><AiFillGithub /></a>
                        <a href="#" target="_blank" rel="noopener noreferrer"><AiFillYoutube /></a>
                    </div>
                </div>
                <span>@ Copyright 2023. 식테크맨. All Rights Reserved.</span>
            </div>
            <nav>
                <p>FrontEnd</p>
                <div>
                    <div>
                        <span>이민훈</span>
                        <a href="https://github.com/lmimoh/lmimoh" target="_blank" rel="noopener noreferrer"><AiFillGithub /></a>
                        <a href="https://velog.io/@dlalsgns9039" target="_blank" rel="noopener noreferrer"><FaMicroblog /></a>
                    </div>
                    <div>   
                        <span>강래헌</span>
                        <a href="https://github.com/kanglaeheon" target="_blank" rel="noopener noreferrer"><AiFillGithub /></a>
                        <a href="https://leondevlog.notion.site/leondevlog/Leon-55db3544a6a34c85b70d5d90457be0de" target="_blank" rel="noopener noreferrer"><FaMicroblog /></a>
                    </div>
                    <div>
                        <span>장은지</span>
                        <a href="https://github.com/ohmegle" target="_blank" rel="noopener noreferrer"><AiFillGithub /></a>
                        <a href="https://velog.io/@omegle3333" target="_blank" rel="noopener noreferrer"><FaMicroblog /></a>
                    </div>
                </div>
                <p>BackEnd</p>
                <div>
                    <div>
                        <span>김태현</span>
                        <a href="https://github.com/taebong98" target="_blank" rel="noopener noreferrer"><AiFillGithub /></a>
                        <a href="https://www.notion.so/taebong98/Home-44c0e3bd6cb64508904849d65e8ea11d" target="_blank" rel="noopener noreferrer"><FaMicroblog /></a>
                    </div>
                    <div>
                        <span>김혜인</span>
                        <a href="https://github.com/gimhae-person" target="_blank" rel="noopener noreferrer"><AiFillGithub /></a>
                        <a href="https://www.notion.so/4c5f73b2f73c4972b84388bc2fa00ed4?v=2a00c6a25c4549aebedc10d0c2472e99" target="_blank" rel="noopener noreferrer"><FaMicroblog /></a>
                    </div>
                    <div>
                        <span>한대호</span>
                        <a href="https://github.com/oheadnah" target="_blank" rel="noopener noreferrer"><AiFillGithub /></a>
                        <a href="https://oheadnah.tistory.com/" target="_blank" rel="noopener noreferrer"><FaMicroblog /></a>
                    </div>
                </div>
            </nav>
           
        </Wrapper>
    )
};

export default Footer;