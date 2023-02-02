import styled from "styled-components";
import Slider from "./Slider";
import FeedInteraction from "./FeedInteraction";
import Comments from "./Comments";
import { useEffect, useState } from "react";
import CloseBtn from "../public/CloseBtn";
import { exchangeTime } from "../../util/exchangeTime";
import defaultImg from "../../assets/img/profile.jpg";
import Cookie from "../../util/Cookie";
import { BiDotsHorizontalRounded } from "react-icons/bi";
import useModal from "../../hooks/useModal";
import DeleteModal from "./DeleteModal";

const Wrapper = styled.div`
    display: flex;
    position: fixed;
    top:50%;
    left:50%;
    transform:translate(-50%, -50%);
    width: 1240px;
    height: 700px;
    background-color: white;
    z-index: 1000;

    .none {
        display: none;
    }

    @media screen and (max-width: 1255px) {
        width: 900px;
        height: 700px;
    }

    @media screen and (max-width: 1024px) {
        width: 400px;
        height: 700px;
        flex-direction: column;

        svg {
            font-size: 20px;
        }
    }
`;

const StyledSlider = styled.div`
    width: 50%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    background-color: black;

    @media screen and (max-width: 1255px) {
        width: 50%;
        height: 100%;
    }

    @media screen and (max-width: 1024px){
        width: 100%;
        height: 300px;
    }
`

const StyledInteraction = styled.div`
    width: 50%;
    height: 100%;
    display: flex;
    flex-direction: column;
    background-color: white;
    border-left: 1px solid #DBDBDB;

    .profile {
        padding: 10px 0px 0px 10px;
        display: flex;
        align-items: center;

        img {
            width: 40px;
            height: 40px;
            border-radius: 50px;
            margin-right: 10px;
        }

        > span:nth-child(2){
            font-size: 18px;
            margin-right: 10px;
        }

        > span:last-of-type {
            font-size: 13px;
            margin-right: 5px;
        }

        > div {
            margin : 0px 5px 0px auto;
        }
    }

    @media screen and (max-width: 1255px) {
        width: 50%;
    }

    @media screen and (max-width: 1024px) {
        width: 100%;
        height: 100%;
    }
`;

const View = ({ handleModal, curPost, handleChange, handleCommentMenu, setCommentId, handleCurPost, handleEdit }) => {
    const { open, close, Modal } = useModal();
    const cookie = new Cookie();
    const [ menuOpend, setMenuOpend ] = useState(false);

    const handleMenu = () => setMenuOpend(!menuOpend);

    useEffect(() => {
        document.getElementById("bg").addEventListener("click", () => {
            handleModal();
        })
    },[handleModal])

    return (
            <Wrapper>
                <Modal>
                    <p onClick={handleEdit}>게시글 수정하기</p>
                    <p onClick={() => {
                        handleMenu();
                        close();
                    }}>게시글 삭제하기</p>
                </Modal>
                { menuOpend ? <DeleteModal handleModal={handleModal} postId={curPost.postingId} handleDelete={handleMenu} handleChange={handleChange} /> : null }
                <StyledSlider>
                    { curPost.postingMedias ?
                        <Slider imgs={curPost.postingMedias} /> : null
                    }
                </StyledSlider>
                <StyledInteraction>
                    <div className="profile">
                        <img src={curPost.profileImage ? curPost.profileImage : defaultImg} alt="profileImg" />
                        <span>{curPost.userName}</span>
                        <span>{curPost.modifiedAt ? exchangeTime(curPost) : ""}</span>
                        { curPost.memberId === Number(cookie.get("memberId")) ?
                            <BiDotsHorizontalRounded onClick={() => {
                                open();
                            }} />
                            : null
                        }
                        <CloseBtn handleEvent={handleModal} />
                    </div>
                    <FeedInteraction post={curPost} type={1} handleChange={handleChange} />
                    <Comments post={curPost} handleChange={handleChange} handleCommentMenu={handleCommentMenu} setCommentId={setCommentId} />
                </StyledInteraction>
            </Wrapper>
    )
}

export default View;