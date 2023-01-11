import styled from "styled-components";
import { allPlants, followPlants } from "../../assets/dummyData/plantImage";

const Wrapper = styled.div`
    width: 500px;
    height: 150px;
`;

const StyledUl = styled.ul`
    margin: 0px;
    padding: 0px;
    list-style: none;
    display: flex;

    li {
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 20%;
    }

    li img {
        width: 100%;
        max-height: 100px;
        padding: 10px;
        border-radius: 50px;
        cursor: pointer;
    }

    li span {
        padding: 10px;
    }
`;

const Recommend = ( { clickedBtn } ) => {

    return (
        <Wrapper>
            <StyledUl>
            {
                clickedBtn === 0 
                ? 
                    allPlants.map((el,idx) => {
                        return (
                            <li key={idx}>
                                <img src={el.img} alt="img" />
                                <span>{el.user_name}</span>
                            </li>
                        );
                    })
                : followPlants.map((el,idx) => {
                    return (
                        <li key={idx}>
                            <img src={el.img} alt="img" />
                            <span>{el.user_name}</span>
                        </li>
                    );
                })
            }
            </StyledUl>
        </Wrapper>
    )
};

export default Recommend;