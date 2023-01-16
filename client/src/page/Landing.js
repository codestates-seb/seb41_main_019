import styled from "styled-components"
import { TfiMinus } from "react-icons/tfi";
import { useState } from "react";
import Logo from "../components/public/Logo";
import Login from "../components/Landing/Login";

const Wrapper = styled.div`
`

const StyledHeader = styled.header`
    position: fixed;
    width: 100%;
    height: 85px;
    border-bottom: 1px solid #dbdbdb;
    z-index: 33;

    nav {
        height : 100%;
        display: flex;
        justify-content: flex-start;
        align-items: center;
        gap : 50px;
    }

    nav ul {
        display: flex;
        flex-direction: row;
        gap: 50px;
        list-style: none;
        padding: 0px;
        margin: 0px;

        li {
            font-size: 18px;
            font-weight: 600;
            color: gray;
            cursor: pointer;
        }

        li svg {
            font-size: 24px;
        }

        .selected {
            color: black;
        }
    }

    @media screen and (max-width: 755px) {

    }
`

const StyledMain = styled.main`
    position: absolute;
    width: 100%;
    height: 100%;
    display: flex;
    padding: 85px 0px 0px 0px;
`

const StyledMenu = styled.div`
    width: 30%;
    height: 100%;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    border-right: 1px solid #DBDBDB;

    ul {
        display: flex;
        flex-direction: column;
        gap: 20px;
        list-style: none;
        padding: 0px 70px 0px 0px;  
        margin: 0px;

        li {
            font-size: 20px;
            font-weight: 800;
            color: gray;
            cursor: pointer;
        }

        li svg {
            position: relative;
            top: 7px;
            left: -15px;
            font-size: 28px;
            visibility: hidden;
        }

        .located {
            color: #07b768;

            svg {
                visibility: visible;
            }
        }
    }
`

const StyledContents = styled.section`
    width: 100%;
    height: 100%;
    overflow: hidden;

    div {
        height: 0%;
        transition: height 0.8s ease;
    }

    .located {
        height: 100%;
    }

    #one {
        background-color: red;
    }

    #two {
        background-color: blue;
    }

    #three {
        background-color: orange;
    }
`

const Landing = () => {
    const [ selected, setSelected ] = useState(0);
    const [ located, setLocated ] = useState(0);
    let timer;

    const handleScroll = (e) => {
        if(!timer) {
            timer = setTimeout(() => {
                timer = null;

                if(e.deltaY > 0 && located < 2) {
                    setLocated(located + 1);
                }
        
                if(e.deltaY < 0 && located > 0) {
                    setLocated(located - 1);
                }
            }, 500)
        }
    }

    return (
        <Wrapper>
            <StyledHeader>
                <nav>
                    <Logo />
                    <ul>
                        <li
                            className={selected === 0 ? "selected" : null}
                            onClick={() => setSelected(0)}>
                            홈
                        </li>
                        <li
                            className={selected === 1 ? "selected" : null}
                            onClick={() => setSelected(1)}>
                            시작하기
                        </li>
                    </ul>
                </nav>
            </StyledHeader>
            {
                selected === 0 ?
                (
                    <StyledMain>
                        <StyledMenu located={located}>
                            <ul>
                                <li
                                    className={located === 0 ? "located" : null}
                                    onClick={() => setLocated(0)}>
                                    <TfiMinus />
                                    자랑하기!
                                </li>
                                <li className={located === 1 ? "located" : null}
                                    onClick={() => setLocated(1)}>
                                    <TfiMinus />
                                    꾸며보기!
                                </li>
                                <li className={located === 2 ? "located" : null}
                                    onClick={() => setLocated(2)}>
                                    <TfiMinus />
                                    소통하기!
                                </li>
                            </ul>
                        </StyledMenu>
                        <StyledContents onWheel={handleScroll}>
                            <div 
                                id="one"
                                className={located === 0 ? "located" : null}>

                            </div>
                            <div 
                                id="two"
                                className={located === 1 ? "located" : null}>

                            </div>
                            <div 
                                id="three"
                                className={located === 2 ? "located" : null}>

                            </div>
                        </StyledContents>
                    </StyledMain>
                ) : (
                    <Login />
                )
            }
        </Wrapper>
    )
}

export default Landing;