import styled from "styled-components"
import { TfiMinus } from "react-icons/tfi";
import { useState } from "react";
import { Content } from "./Content";
import { BsDot } from "react-icons/bs"

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
    padding: 0px 0px 85px 0px;

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

    .dot {
        display: none;
    }

    @media screen and (max-width: 1000px) {
        ul {
            padding: 0px;
        }

        span {
            display: none;
        }

        svg {
            display: none;
        }
        
        .dot {
            display: block;
            visibility: visible;
        }
    }
`

const StyledContents = styled.section`
    width: 100%;
    height: 100%;
    overflow: hidden;

    > div {
        transform: ${({located}) => `translateY(${-located * 33}%)`};
    }
`

const Main = () => {
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
            }, 300)
        }
    }

    return (
        <StyledMain>
            <StyledMenu located={located}>
                <ul>
                    <li
                        className={located === 0 ? "located" : null}
                        onClick={() => setLocated(0)}>
                        <TfiMinus />
                        <BsDot className="dot"/>
                        <span>자랑하기!</span>
                    </li>
                    <li className={located === 1 ? "located" : null}
                        onClick={() => setLocated(1)}>
                        <TfiMinus />
                        <BsDot className="dot"/>
                        <span>꾸며보기!</span>
                    </li>
                    <li className={located === 2 ? "located" : null}
                        onClick={() => setLocated(2)}>
                        <TfiMinus />
                        <BsDot className="dot"/>
                        <span>소통하기!</span>
                    </li>
                </ul>
            </StyledMenu>
            <StyledContents onWheel={handleScroll} located={located}>
                <Content located={located}/>
            </StyledContents>
        </StyledMain>
    )
}

export default Main