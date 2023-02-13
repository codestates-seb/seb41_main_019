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
    width: 450px;
    margin: 0px;
    padding: 0px;
    list-style: none;
    display: flex;
    color: #222426;
    gap: 10px;
    height: 116px;

    @media screen and (max-width: 770px) {
        width: 400px;
    }
`;

const StyledDiv = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    height: 116px;
    width: 100%;
    background-color: white;
    display: ${({clickedBtn}) => clickedBtn === 0 ? 'none': null };

    span {
        color: rgb(142,142,142);
    }
`;

const Recommends = ({ change, handleModal, setCurPost }) => {
    const [ clickedBtn, setClickedBtn ] = useState(0); 
    const [ posts, setPosts ] = useState([]);
    const cookie = new Cookie();   

    useEffect(() => {
        axios({
            method: "get",
            url: `${process.env.REACT_APP_API}/posts/${clickedBtn === 0 ? "" : "follow/"}popular?page=1&size=10`,
            headers: { Authorization: cookie.get("authorization") }
            }).then(res => {
                setPosts(res.data.data);
            }).catch(e => {});
    }, [change, clickedBtn]);

    return (
        <StyledSection id="movoTo">
            <StyledHeader>
                <button className={clickedBtn === 0 ? 'active' : null} onClick={(e) => setClickedBtn(0)}>All</button>
                <button className={clickedBtn === 1 ? 'active' : null} onClick={(e) => setClickedBtn(1)}>Follow</button>
            </StyledHeader>  
            <StyledUl>
                {
                    posts.length > 0  ? 
                        posts.slice(0, 5).map((post, idx) => {
                            return (
                                <Recommend key={idx} post={post} clickedBtn={clickedBtn} handleModal={handleModal} setCurPost={setCurPost}/>
                            );
                        }) 
                    : <StyledDiv clickedBtn={clickedBtn}>
                        <span>Follow 목록이 존재하지 않습니다.</span>
                    </StyledDiv>
                }  
            </StyledUl>
        </StyledSection>
    )
};

export default Recommends;