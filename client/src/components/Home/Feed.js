import styled from "styled-components";
import Post from "./Post";
import { ReactComponent as NoContent } from "../../assets/svg/monstera.svg";
import { useState, useEffect, useRef } from "react";
import axios from "axios";
import Cookie from "../../util/Cookie";
import useScroll from "../../hooks/useScroll";

const Wrapper = styled.section`
    position: relative;
    display: flex;
    width: 100%;
    flex-direction: column;
    align-items: center;
    justify-content: center;

    .no-content {
        display: flex;
        flex-direction: column;
        align-items: center;
        position: fixed;
        top: 40%;
        width: 300px;
        height: 300px;
        opacity: 0.3; 
    }
`

const Feed = ({ menu, handleMenu, handleModal, handleDelete, handleCurPost, handleEdit, change, setPostId, postId, handleChange, curPost }) => { 
    const [ posts, setPosts ] = useState([]);
    const [ page, setPage ] = useState(1);
    const [ observe, unObserve ] = useScroll(handlePage);
    const checkPost = useRef(null);
    const cookie = new Cookie();

    useEffect(() => {
        axios({
            method: "get",
            url: `${process.env.REACT_APP_API}/posts?page=1&size=${page * 10}`,
            headers: { Authorization: cookie.get("authorization") }
            }).then(res => {
                setPosts(res.data.data)
                if(curPost) handleCurPost(...res.data.data.filter(post => post.postingId === postId));
            })
            .catch(e => {
               console.log(e);  
            });
    }, [change])

    useEffect(() => {
        axios({
            method: "get",
            url: `${process.env.REACT_APP_API}/posts?page=${page}&size=10`,
            headers: { Authorization: cookie.get("authorization") }
            }).then(res => {
                setPosts([...posts, ...res.data.data]);
                setTimeout(() => {
                    if(page * 10 < res.data.pageInfo.totalElements) {
                        observe(checkPost.current);
                    }
                }, 150)
            })
            .catch(e => {
               console.log(e);  
            });
    }, [page])


    function handlePage() {
        setPage(page + 1);
        unObserve(checkPost.current);
    }
  
    return (
        <Wrapper>
            { posts.length < 1 
                ?
                    <div className="no-content"> 
                        <NoContent />
                        <span>게시물이 없습니다.</span> 
                    </div>
                : posts.map((post, idx) => {
                    return (<Post menu={menu} handleMenu={handleMenu} setPostId={setPostId} post={post} key={idx} handleModal={handleModal} handleChange={handleChange}
                            handleCurPost={handleCurPost} handleDelete={handleDelete} handleEdit={handleEdit} change={change} 
                            checkPost={idx === posts.length - 3 ? checkPost : null}/>)
                })
            }
        </Wrapper>
    )
}

export default Feed;