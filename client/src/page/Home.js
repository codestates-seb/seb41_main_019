import Recommends from "../components/Home/Recommends";
import Feeds from "../components/Home/Feeds";
import styled from "styled-components";
import View from "../components/Home/View";
import { useState } from "react";

const StyledMain = styled.main`
    margin: 0px 300px 0px 270px;

    @media screen and (max-width: 1255px) {
        margin: 0px 300px 0px 60px;
    }

    @media screen and (max-width: 770px) {
        margin: 60px 0px 0px 0px;
    }
`;

const Home = ({ handleIsCovered }) => {
    const [modal, setModal] = useState(false);

    const handleModal = () => {
        handleIsCovered();
        setModal(!modal);
    }

    return (
        <>
        <View modal={modal} handleModal={handleModal} />
        <StyledMain>
            <Recommends />
            <Feeds handleModal={handleModal}/>
        </StyledMain>
        </>
    )
}

export default Home;