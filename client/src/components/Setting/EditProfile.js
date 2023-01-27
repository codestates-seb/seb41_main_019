import styled from "styled-components";
import { BlueBtn } from "../public/BlueBtn";
import axios from "axios";
import Cookie from "../../util/Cookie";
import { useEffect, useState, useRef, useCallback } from "react";

const Wrapper = styled.div`
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 30px;
    margin: 30px 0px 0px 70px;

    p {
        margin: 0px 0px 0px 15px;
        font-size: 12px;
    }

    div {
        display: flex;
        justify-content: space-around;
        gap: 0px;

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

    .red {
        p {
            color: red;
        }
    }
`;

const EditProfile = ({ open, close, name, text, location, img, setName, setText, setLocation, setImg }) => {
    const cookie = new Cookie();
    const [ file, setFile ] = useState(null);
   
    // 별명, 소개, 지역 오류 메세지
    // const [ nameMessage, setNameMessage ] = useState("");
    // const [ introMessage, setIntroMessage ] = useState("");
    // const [ locationMessage, setLocationMessage ] = useState("");

    // 유효성 검사
    const [ isName, setIsName ] = useState(false);
    const [ isIntro, setIsIntro ] = useState(false);
    const [ isLocal, setIsLocal ] = useState(false);

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
        }).catch(e => {
            console.log(e);
        })
    };

    const handleSubmit = () => {
        console.log(name, text, location);
        if(name.length < 2 || name.length > 6) {
            setIsName(true);
            return;
        } 

        setIsName(false);

        if(text.length > 30) {
            setIsIntro(true);
            return;
        }

        setIsIntro(false);
        
        if(location.length > 10) {
            setIsLocal(true);
            return;
        }

        setIsLocal(false);

        editProfile();
        open();
    };

    const updateImg = (e) => {
        setFile(e.target.files[0]);
    };

    useEffect(() => {
        if(file) {
            const formData = new FormData();
            formData.append("profileImage", file);

            axios({
                method: "post",
                url: `http://13.124.33.113:8080/members/${cookie.get("memberId")}/profileimage`,
                data: formData,
                headers: { "Content-Type": "multipart/form-data", Authorization: cookie.get("authorization") }
            }).then(res => {
                setImg(res.data.profileImage);
            }).catch(e => {
                console.log(e);
            });
        }
    }, [file])

    return (
        <Wrapper>
            <div className="profile">
                <div className="profileImg">
                    <img src={img} alt="img"/>
                </div>
                <div>
                    <span>{name}</span>
                    <label htmlFor="file">프로필 사진 바꾸기</label>
                    <input onChange={updateImg} id="file" type="file" hidden />
                </div>
            </div>
            <div className={isName ? "red" : null}>
                <label htmlFor="username">별명</label>
                <input id="username" value={name} 
                    onChange={(e) => setName(e.target.value)} 
                    />
                <p>별명은 한글이나 영문 2~6글자까지 가능합니다.</p>
            </div>
            <div className={isIntro ? "red" : null}>
                <label htmlFor="introduction">소개</label>
                <textarea id="introduction" value={text} 
                    onChange={(e) => setText(e.target.value)} placeholder="..."
                    />
                <p>소개는 0~30자까지 가능합니다.</p>
            </div>  
            <div className={isLocal ? "red" : null}>
                <label htmlFor="location">소속</label>
                <input id="location" value={location} 
                    onChange={(e) => setLocation(e.target.value)} placeholder="..."
                    />
                <p>소속은 0~10글까지 가능합니다.</p>
            </div>  
            <BlueBtn onClick={() => {
                handleSubmit();
            }}>변경하기</BlueBtn>
            
        </Wrapper>
    )
};

export default EditProfile;
