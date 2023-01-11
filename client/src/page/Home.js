import Recommends from "../components/Home/Feeds";
import Feeds from "../components/Home/Recommends";
import styled from "styled-components";

const StyledMain = styled.main`
    margin: 0px 0px 0px 270px;

    @media screen and (max-width: 1255px) {
        margin: 0px 0px 0px 60px;
    }

    @media screen and (max-width: 770px) {
        margin: 60px 0px 0px 0px;
        max-height: 938px;
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