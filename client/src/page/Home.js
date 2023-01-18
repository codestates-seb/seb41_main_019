import Recommends from "../components/Home/Recommends";
import Feed from "../components/Home/Feed";
import styled from "styled-components";
import View from "../components/Home/View";
import { useState,useEffect } from "react";
import DeleteModal from "../components/Home/DeleteModal";
import axios from "axios";
import Cookie from "../util/Cookie";
import EditPost from "../components/public/Post/EditPost";

const StyledMain = styled.main`

    @media screen and (max-width: 770px) {
        margin: 60px 0px 0px 0px;
    }
`;

const Home = ({ handleIsCovered }) => {
    const [ modal, setModal ] = useState(false);
    const [ edit, setEdit ] = useState(false);
    const [ deleteMenu, setDeleteMenu ] = useState(false);
    const [ post, setPost ]= useState({});
    const [ curPost, setCurPost ] = useState(null);
    const cookie = new Cookie();

    const handleModal = () => {
        handleIsCovered();
        setModal(!modal);
    };

    const handleDelete = () => {
        handleIsCovered();
        setDeleteMenu(!deleteMenu);
    };

    const handleCurPost = (postId) => {
        setCurPost(postId);
    };

    const handleEdit = () => {
        handleIsCovered();
        setEdit(!edit);
    };

    return (
        <>
            { edit ? <EditPost curPost={curPost} handleEdit={handleEdit}/> : null }
            { modal ? <View handleModal={handleModal} curPost={curPost} /> : null }
            { deleteMenu ? <DeleteModal handleDelete={handleDelete} /> : null }
            <StyledMain>
                <Recommends />
                <Feed handleModal={handleModal} handleDelete={handleDelete} handleEdit={handleEdit} handleCurPost={handleCurPost} />
            </StyledMain>
        </>
    )
}

export default Home;