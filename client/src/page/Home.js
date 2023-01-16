import Recommends from "../components/Home/Recommends";
import Feeds from "../components/Home/Feeds";
import styled from "styled-components";
import View from "../components/Home/View";
import FeedMenu from "../components/Home/FeedMenu";
import { useState } from "react";
import DeleteModal from "../components/Home/DeleteModal";

const StyledMain = styled.main`

    @media screen and (max-width: 770px) {
        margin: 60px 0px 0px 0px;
    }
`;

const Home = ({ handleIsCovered }) => {
    const [modal, setModal] = useState(false);
    const [deleteMenu, setDeleteMenu] = useState(false);

    const handleModal = () => {
        handleIsCovered();
        setModal(!modal);
    }

    const handleDelete = () => {
        handleIsCovered();
        setDeleteMenu(!deleteMenu);
    }

    return (
        <>
            {modal ? <View handleModal={handleModal} /> : null}
            {deleteMenu ? <DeleteModal handleDelete={handleDelete} /> : null}
            <StyledMain>
                <Recommends />
                <Feeds handleModal={handleModal} handleDelete={handleDelete}/>
            </StyledMain>
        </>
    )
}

export default Home;