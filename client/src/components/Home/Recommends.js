import { useState, useEffect } from "react";
import styled from "styled-components";
import Recommend from "./Recommend";
import axios from "axios";
import Cookie from "../../util/Cookie";

const StyledSection = styled.section`
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 0px auto 40px auto;
`;

const StyledHeader = styled.header`
    height: 50px;
    display: flex;
    align-items: center;
    gap: 80px;
    margin-top: 8px;

    .active {
        border-bottom: 4px solid #A7C4BC;
        font-weight: 600;
    }

    button {
        font-size: 1rem;
        background: white;
        letter-spacing: 1px;
        border: 0px;
        cursor: pointer;
    }
`;
const StyledUl = styled.ul`
     width: 500px;
    margin: 0px;
    padding: 0px;
    list-style: none;
    display: flex;
    color: #222426;
    gap: 10px;

    @media screen and (max-width: 770px) {
        width: 460px;
    }
`;

const Recommends = ({ change }) => {
    const [ clickedBtn, setClickedBtn ] = useState(0); 
    const [ allPosts, setAllPosts ] = useState("");
    const [ followPosts, setFollowPosts ] = useState("");
    const cookie = new Cookie();   

    useEffect(() => {
        axios({
            method: "get",
            url: `http://13.124.33.113:8080/posts/popular?page=1&size=10`,
            headers: { Authorization: cookie.get("authorization") }
            }).then(res => {
                setAllPosts(res.data.data);
            })
            .catch(e => {
            });
    }, [change]);

    useEffect(() => {
        axios({
            method: "get",
            url: `http://13.124.33.113:8080/posts/follow/popular?page=1&size=10`,
            headers: { Authorization: cookie.get("authorization") }
            }).then(res => {
                setFollowPosts(res.data.data);
            })
            .catch(e => {
                console.log(e);
            });
    }, [change]);
   
    return (
        <StyledSection>
            <StyledHeader>
                <button className={clickedBtn === 0 ? 'active' : null} onClick={(e) => setClickedBtn(0)}>All</button>
                <button className={clickedBtn === 1 ? 'active' : null} onClick={(e) => setClickedBtn(1)}>Follow</button>
            </StyledHeader>        
            <StyledUl>
               { allPosts && clickedBtn === 0 ? 
                    allPosts.filter((el, idx) => idx <= 4).map((allPost,idx) => {
                        return (
                            <Recommend key={idx} allPost={allPost} clickedBtn={clickedBtn} />
                        );
                    })
                   : null     
                }
                { followPosts && clickedBtn === 1 ? 
                    followPosts.filter((el,idx) => idx <= 4).map((followPost,idx) => {
                        return (
                            <Recommend key={idx} followPost={followPost} clickedBtn={clickedBtn}/>
                        );
                    }) 
                    : null
                }
            </StyledUl>
        </StyledSection>
    )
};

export default Recommends;