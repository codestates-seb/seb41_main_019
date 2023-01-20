import styled from "styled-components";
import { FiUserPlus } from "react-icons/fi";
import { FaUserFriends } from "react-icons/fa";
import { BiDotsVerticalRounded } from "react-icons/bi";
import Slider from "./Slider";
import FeedInteraction from "./FeedInteraction";
import FeedMenu from "./FeedMenu";
import { useEffect, useState } from "react";
import { exchangeTime } from "../../util/exchangeTime";
import defaultImg from "../../assets/img/profile.jpg"
import Cookie from "../../util/Cookie";
import axios from "axios";

const Wrapper = styled.div`
    position: relative;
    width: 500px;
    height: 760px;
    padding-top: 25px;
    margin-bottom: 20px;
    border-top: 1px solid #DBDBDB;

    img {
        width: 100%;
        height: 100%;
        border-radius: 3px;
    }

    svg {
        font-size: 25px;
        color: #222426;
        cursor: pointer;
    }

    @media screen and (max-width: 770px) {
        width: 460px;
        height: 750px;
    }
`;

const StyledHeader = styled.div`
    width: 100%;
    display: flex;
    align-items: flex-end;
    margin-bottom: 10px;

    img {
        width: 50px;
        height: 50px;
        border-radius: 45px;
        margin-right: 10px;
        cursor: pointer;
    }

    div {
        display: flex;
        flex-direction: column;
    }

    div > span:first-child{
        color: #222426;
        font-size: 16px;
        font-weight: 500;
        margin-bottom: 3px;
        letter-spacing: 1px;
    }

    div > span:nth-child(2) {
        font-size: 14px;
        color: gray;
        letter-spacing: 1px;
    }

    .icons {
        flex-direction: row;
        margin : 0px 0px 5px auto;
    }

    /* follow icon */
    div > svg:first-child {
       
    }
`;

const Post = ({ post, handleModal, handleDelete, handleCurPost, handleEdit }) => {
    const [menu, setMenu] = useState(false);
    const [follow, setFollow] = useState([]);
    const cookie = new Cookie();

    const handleMenu = () => {
        setMenu(!menu);
    };

    const deleteFollow = () => {
        axios({ 
            method: "delete", 
            url: `http://13.124.33.113:8080/members/4`,
            headers: { Authorization: cookie.get("authorization") }
        }).then(res => {
            console.log(res);
        })
        .catch(e => {
            console.log(e);
        });
    };

    const addFollow = () => {
        axios({
            method: "post",
            url: `http://13.124.33.113:8080/followings/${post.memberId}`,
            headers: { Authorization: cookie.get("authorization") }
            }).then(res => {
                
            })
            .catch(e => {
                console.log(e);
            });
    };

    useEffect(() => {
        axios({
            method: "get",
            url: `http://13.124.33.113:8080/members/${post.memberId}`,
            headers: { Authorization: cookie.get("authorization") }
            }).then(res => {
                setFollow(res.data.data.followerList);
            })
            .catch(e => {
               console.log(e);
            });
    },[])

    return (
        <Wrapper>
            { menu ? <FeedMenu handleDelete={handleDelete} handleMenu={handleMenu} handleEdit={handleEdit} /> : null }
            <StyledHeader>
                <img src={post.profileImage ? post.profileImage : defaultImg} alt="profileImg" />
                <div>
                    <span>{post.userName}</span>
                    <span>{exchangeTime(post)}</span>
                </div>
                <div className="icons">
                    {   
                        follow.filter(e => e.followerId === Number(cookie.get("memberId"))).length > 0 ||
                        post.memberId === Number(cookie.get("memberId")) ?
                            null : <FiUserPlus onClick={addFollow} />
                    }
                    { post.memberId === Number(cookie.get("memberId")) ?
                        <BiDotsVerticalRounded onClick={() => {
                            handleMenu(); 
                            handleCurPost(post);
                            }} />
                        : null
                    }
                </div>
            </StyledHeader>
            { post.postingMedias.length > 0 ?
                <Slider imgs={post.postingMedias} /> : null
            }
            <FeedInteraction post={post} setModal={handleModal} handleCurPost={handleCurPost} />
        </Wrapper>
    );
}

export default Post;