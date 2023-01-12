import Recommends from "../components/Home/Recommends";
import Feeds from "../components/Home/Feeds";
import styled from "styled-components";

const StyledMain = styled.main`
    margin: 0px 300px 0px 270px;

    @media screen and (max-width: 1255px) {
        margin: 0px 0px 0px 60px;
    }

    @media screen and (max-width: 770px) {
        margin: 60px 0px 0px 0px;
    }
`;

const Home = () => {
    return (
        <StyledMain>
            <Recommends />
            <Feeds />
        </StyledMain>
    )
}

export default Home;