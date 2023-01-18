import Recommends from "../components/Home/Recommends";
import Feed from "../components/Home/Feed";
import styled from "styled-components";
import View from "../components/Home/View";
import { useState,useEffect } from "react";
import DeleteModal from "../components/Home/DeleteModal";
import axios from "axios";
import Cookie from "../util/Cookie";

const StyledMain = styled.main`

    @media screen and (max-width: 770px) {
        margin: 60px 0px 0px 0px;
    }
`;

const Home = ({ handleIsCovered }) => {
    const [modal, setModal] = useState(false);
    const [deleteMenu, setDeleteMenu] = useState(false);
    const [ posts, setPosts ] = useState([]);
    const cookie = new Cookie();

    useEffect(() => {
        axios({
            method: "get",
            url: "http://13.124.33.113:8080/posts?page=1&size=10",
            headers: { Authorization: cookie.get("authorization") }
            }).then(res => {
                setPosts(res.data.data);
            })
            .catch(e => {
               console.log(e);
            });
    }, [])

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
            { modal ? <View handleModal={handleModal} posts={posts} /> : null }
            { deleteMenu ? <DeleteModal handleDelete={handleDelete} /> : null }
            <StyledMain>
                <Recommends />
                <Feed handleModal={handleModal} handleDelete={handleDelete} posts={posts} />
            </StyledMain>
        </>
    )
}

export default Home;