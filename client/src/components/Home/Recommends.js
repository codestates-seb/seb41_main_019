import { useState } from "react";
import styled from "styled-components";
import Recommend from "./Recommend";

const StyledSection = styled.section`
    display: flex;
    flex-direction: column;
    align-items: center;
`;

const StyledHeader = styled.header`
    display: flex;
    align-items: center;
    justify-content: space-around;
    margin: 0;
    width: 500px;
    height: 50px;

        .active {
            border-bottom: 4px solid #A7C4BC;
            font-weight: 600;
        }

        button {
            font-size: 1rem;
            background: white;
            letter-spacing: 1px;
            border: 0px;
            cursor: pointer;
        }
`;

const Recommends = () => {

    const [ clickedBtn, setClickedBtn ] = useState(0);

    return (
        <StyledSection>
            <StyledHeader>
                <button className={clickedBtn === 0 ? 'active' : null} onClick={(e) => setClickedBtn(0)}>All</button>
                <button className={clickedBtn === 1 ? 'active' : null} onClick={(e) => setClickedBtn(1)}>Follow</button>
            </StyledHeader>        
            <Recommend clickedBtn={clickedBtn} />
        </StyledSection>
    )
};

export default Recommends;