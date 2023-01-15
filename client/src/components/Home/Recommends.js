import { useState } from "react";
import styled from "styled-components";
import Recommend from "./Recommend";
import { allPlants, followPlants } from "../../assets/dummyData/plantImage";

const StyledSection = styled.section`
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 0px auto 40px auto;
`;

const StyledHeader = styled.header`
    height: 50px;
    display: flex;
    align-items: center;
    gap: 40px;
    margin-top: 8px;

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
const StyledUl = styled.ul`
    margin: 0px;
    padding: 0px;
    list-style: none;
    display: flex;
    color: #222426;
    gap: 10px;
`;

const Recommends = () => {
    const [ clickedBtn, setClickedBtn ] = useState(0);

    return (
        <StyledSection>
            <StyledHeader>
                <button className={clickedBtn === 0 ? 'active' : null} onClick={(e) => setClickedBtn(0)}>All</button>
                <button className={clickedBtn === 1 ? 'active' : null} onClick={(e) => setClickedBtn(1)}>Follow</button>
            </StyledHeader>        
            <StyledUl>
            { clickedBtn === 0 
                ? 
                    allPlants.map((el,idx) => {
                        return <Recommend key={idx} el={el} />;
                    })
                : followPlants.map((el,idx) => {
                    return <Recommend key={idx} el={el} />;
                })
            }
            </StyledUl>
        </StyledSection>
    )
};

export default Recommends;