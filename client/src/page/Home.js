import Recommends from "../components/Home/Recommends";
import Feeds from "../components/Home/Feeds";
import styled from "styled-components";
import View from "../components/Home/View";
import FeedModal from "../components/Home/FeedModal";
import { useState } from "react";

const StyledMain = styled.main`
    @media screen and (max-width: 1255px) {
        margin: 0px 0px 0px 60px;
    }

    @media screen and (max-width: 770px) {
        margin: 60px 0px 0px 0px;
    }
`;

const Home = ({ handleIsCovered }) => {
    const [modal, setModal] = useState(false);
    const [menu, setMenu] = useState(false);

    const handleModal = () => {
        handleIsCovered();
        setModal(!modal);
    }

    const handleMenu = () => {
        handleIsCovered();
        setMenu(!menu);
    }

    return (
        <>
            {modal ? <View handleModal={handleModal} /> : null}
            {menu ? <FeedModal handleMenu={handleMenu}/> : null}
            <StyledMain>
                <Recommends />
                <Feeds handleModal={handleModal} handleMenu={handleMenu}/>
            </StyledMain>
        </>
    )
}

export default Home;