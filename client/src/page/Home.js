import Recommends from "../components/Home/Recommends";
import Feed from "../components/Home/Feed";
import styled from "styled-components";
import View from "../components/Home/View";
import { useState } from "react";
import DeleteModal from "../components/Home/DeleteModal";
import EditPost from "../components/public/Post/EditPost";
import CommentModal from "../components/Home/CommentModal";

const StyledMain = styled.main`

    @media screen and (max-width: 770px) {
        margin: 60px 0px 0px 0px;
    }
`;

const Home = ({ handleIsCovered, change, handleChange }) => {
    const [ modal, setModal ] = useState(false);
    const [ edit, setEdit ] = useState(false);
    const [ commentMenu, setCommentMenu ] = useState(false);
    const [ deleteMenu, setDeleteMenu ] = useState(false);
    const [ curPost, setCurPost ] = useState(null);
    const [ postId, setPostId ] = useState(null);
    const [ commentId, setCommentId ] = useState(null);
    const [menu, setMenu] = useState(false);
 
    const handleModal = () => {
        handleIsCovered();
        setModal(!modal);
        setCommentMenu(false);
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

    const handleCommentMenu = () => {
        setCommentMenu(!commentMenu);
    }

    const handleMenu = () => {
        setMenu(!menu);
    };

    return (
        <>
            { commentMenu ? <CommentModal post={curPost} handleCommentMenu={handleCommentMenu} handleChange={handleChange} commentId={commentId} /> : null}
            { edit ? <EditPost curPost={curPost} handleEdit={handleEdit} handleChange={handleChange} change={change} /> : null }
            { modal ? <View deleteMenu={deleteMenu} menu={menu} handleMenu={handleMenu} handleCurPost={handleCurPost} 
            handleModal={handleModal} curPost={curPost} handleChange={handleChange} handleCommentMenu={handleCommentMenu} 
            setCommentId={setCommentId} handleDelete={handleDelete} handleEdit={handleEdit} />  : null }
            { deleteMenu ? <DeleteModal postId={curPost.postingId} handleDelete={handleDelete} handleChange={handleChange} /> : null }
            <StyledMain>
                <Recommends change={change} handleModal={handleModal} setCurPost={setCurPost} />
                <Feed menu={menu} handleMenu={handleMenu} handleModal={handleModal} handleDelete={handleDelete} handleEdit={handleEdit} curPost={curPost}
                    handleCurPost={handleCurPost} change={change} setPostId={setPostId} postId={postId} handleChange={handleChange} />
            </StyledMain>
        </>
    )
}

export default Home;