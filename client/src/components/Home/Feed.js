import styled from "styled-components";
import Post from "./Post";
import { ReactComponent as NoContent } from "../../assets/svg/monstera.svg";
import { useState, useEffect } from "react";
import axios from "axios";
import Cookie from "../../util/Cookie";

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

const Feed = ({ handleModal, handleDelete, handleCurPost, handleEdit, change, handleChange }) => { 
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
    }, [change])
  
    return (
        <Wrapper>
            { posts.length < 1 
                ?
                    <div className="no-content"> 
                        <NoContent />
                        <span>게시물이 없습니다.</span> 
                    </div>
                : posts.map((post, idx) => {
                    return (<Post post={post} key={idx} handleModal={handleModal} handleChange={handleChange} change={change}
                                handleCurPost={handleCurPost} handleDelete={handleDelete} handleEdit={handleEdit} />)
                })
            }
        </Wrapper>
    )
}

export default Feed;