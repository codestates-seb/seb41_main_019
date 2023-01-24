import styled from "styled-components";
import EditProfile from "../components/Setting/EditProfile";
import DeleteProfile from "../components/Setting/DeleteProfile";
import { useState, useEffect } from "react";
import axios from "axios";
import Cookie from "../util/Cookie";
import defaultImg from "../assets/img/profile.jpg";
import Footer from "../components/public/Footer";

const Wrapper = styled.div`
    display: flex;
    height: 80%;
    width: 900px;
    position: fixed;
    top:50%;
    left:50%;
    transform:translate(-50%, -50%);
 
    border: 1px solid #dbdbdb;

    input, textarea {
        width :300px;
        outline: none;
        border: 1px solid #dbdbdb;
        border-radius: 5px;

        :focus {
            outline: none;
            box-shadow: 0 0 6px #5e8b7e;
        }
    }

    input {
        height: 30px;
    }
`;

const StyledMenu = styled.div`
    display: flex;
    width: 30%;
    gap: 20px;
    flex-direction: column;
    border-right: 1px solid #dbdbdb;


    p {
        margin: 0;
        padding: 20px;
    }

    .active {
        border-left: 3px solid #374435;
    }
`

const Setting = () => {
    const [ isClicked, setIsClicked ] = useState(0);
    const [ name, setName ] = useState("");
    const [ text, setText ] = useState("");
    const [ location, setLocation] = useState("");
    const [ img, setImg ] = useState(null);
    const cookie = new Cookie();

    useEffect(() => {
        axios({
            method: "get",
            url: `http://13.124.33.113:8080/members/${cookie.get("memberId")}`,
            headers: { Authorization : cookie.get("authorization") }
        }).then(res => {
            const user = res.data.data;
            console.log(user);
            setName(user.userName);
            setText(user.profileText);
            setLocation(user.location);
            user.profileImage ? setImg(user.profileImage) : setImg(defaultImg);
        }).catch(e => {
            console.log(e);
        })
    }, [])

    return (
        <Wrapper>
            <StyledMenu>
                <p className={isClicked === 0 ? "active" : null} onClick={() => setIsClicked(0)}>프로필 편집</p>
                <p className={isClicked === 1 ? "active" : null} onClick={() => setIsClicked(1)}>계정 탈퇴</p>
            </StyledMenu>
            { isClicked === 0 
                ? <EditProfile name={name} text={text} location={location} img={img} 
                setName={setName} setText={setText} setLocation={setLocation} /> 
                :  <DeleteProfile name={name} img={img} />
            }
            <Footer />
        </Wrapper>
    )
};

export default Setting;