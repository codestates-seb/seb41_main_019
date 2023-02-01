import styled from "styled-components";
import Slider from "./Slider";
import FeedInteraction from "./FeedInteraction";
import Comments from "./Comments";
import { useEffect } from "react";
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
        height: 600px;
    }

    @media screen and (max-width: 1024px) {
        width: 400px;
        height: 500px;
        flex-direction: column;

        svg {
            font-size: 20px;
        }
    }
`;

const StyledSlider = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    background-color: black;
    @media screen and (max-width: 1024px){
        width: 100%;
        height: 200px;
    }
`

const StyledInteraction = styled.div`
    width: 65%;
    display: flex;
    flex-direction: column;
    background-color: white;
    border-left: 1px solid #DBDBDB;

    > div:first-child {
        @media screen and (max-width: 1024px){
            padding: 10px;
        }
    }

    .profile {
        padding: 10px;
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
            margin : 5px 0px 0px auto;
        }
    }

    @media screen and (max-width: 1255px) {
        width: 100%;
    }

    @media screen and (max-width: 1024px) {
        height: 200px;
    }
`;

const View = ({ deleteMenu, menu, handleMenu, handleModal, curPost, handleChange, handleCommentMenu, setCommentId, handleCurPost, handleDelete, handleEdit }) => {
    const { open, close, Modal } = useModal();
    const cookie = new Cookie();

    useEffect(() => {
        document.getElementById("bg").addEventListener("click", () => {
            handleModal();
        })
    },[handleModal])

    return (
            <Wrapper>
                { menu && 
                    <Modal>
                        <p onClick={handleEdit}>수정하기</p>
                        <p onClick={handleDelete}>삭제하기</p>
                    </Modal>
                }
                { deleteMenu ? <DeleteModal postId={curPost.postingId} handleDelete={handleDelete} handleChange={handleChange} /> : null }
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
                                handleCurPost(curPost);
                                handleMenu();
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