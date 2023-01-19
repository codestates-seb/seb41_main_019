import styled from "styled-components";
import { BlueBtn } from "../public/BlueBtn";
import { useEffect, useState } from "react";
import axios from "axios";
import Cookie from "../../util/Cookie";
import defaultImg from "../../assets/img/profile.jpg"

const Wrapper = styled.div`
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 30px;
    margin-top: 50px;

    div {
        display: flex;
        justify-content: space-around;
        gap: 20px;

        label {
            width: 80px;
        }
    }

    .profile {
        display: flex;
        flex-direction: row;
        width: 400px;
        
        div:nth-of-type(2) {
            width: 300px;
            display: flex;
            flex-direction: column;

            label {
                color: blue;
                cursor: pointer;
                width: 50%;
            }
        }
    }
    
    .profileImg {
        width: 50px;
        height: 50px;
        background-color: gray;
        border-radius: 50px;
        overflow: hidden;
        margin: 0px 18px 0px 15px;
    }

    textarea {
        height: 100px;
        resize: none;
    }
`;

const EditProfile = () => {
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

    const editProfile = () => {
        axios({
            method: "patch",
            url: `http://13.124.33.113:8080/members/${cookie.get("memberId")}`,
            headers: { Authorization : cookie.get("authorization") },
            data : {
                userName: name,
                profileText: text,
                location: location
            }
        }).then(res => {
            console.log(res);
        }).catch(e => {
            console.log(e);
        })
    }

    return (
        <Wrapper>
            <div className="profile">
                <div className="profileImg">
                    <img src={img} alt="img"/>
                </div>
                <div>
                    <span>{name}</span>
                    <label htmlFor="profileImg">프로필 사진 바꾸기</label>
                    <input id="profileImg" type="file" hidden />
                </div>
            </div>
            <div>
                <label htmlFor="username">사용자 이름</label>
                <input id="username" value={name} onChange={(e) => setName(e.target.value)}/>
            </div>
            <div>
                <label htmlFor="introduction">소개</label>
                <textarea id="introduction" value={text} onChange={(e) => setText(e.target.value)} placeholder="..."/>
            </div>  
            <div>
                <label htmlFor="location">지역</label>
                <input id="location" value={location} onChange={(e) => setLocation(e.target.value)} placeholder="..."/>
            </div>  
            <BlueBtn onClick={editProfile}>변경하기</BlueBtn>
        </Wrapper>
    )
};

export default EditProfile;
