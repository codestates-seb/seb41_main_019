import styled from "styled-components"
import { RiLeafLine } from "react-icons/ri";
import { TfiMinus } from "react-icons/tfi";
import { useState } from "react";

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

    h2 {
        display: flex;
        align-items: flex-end;
        font-weight: 400;
        letter-spacing: 2px;
        cursor: pointer;
        margin: 0px 0px 0px 15%;

        svg {
            color: #5e8b7e;
            font-size: 25px;
            margin: 0px 0px 3px -2px;
        }
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

const StyledContent = styled.section`
    width: 100%;
    height: 100%;
    background-color: blue;
    overflow: scroll;

    div {
        width: 100%;
        height: 100%;
    }
`

const Landing = () => {
    const [ selected, setSelected ] = useState(0);
    const [ located, setLocated ] = useState(0);
    return (
        <Wrapper>
            <StyledHeader>
                <nav>
                    <h2>
                        <span>IncleaF</span>
                        <RiLeafLine />
                    </h2>
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
                <StyledContent>
                    <div>

                    </div>
                    <div>

                    </div>
                </StyledContent>
            </StyledMain>
        </Wrapper>
    )
}

export default Landing;