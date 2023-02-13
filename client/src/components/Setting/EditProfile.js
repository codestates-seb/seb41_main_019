import styled from "styled-components";
import { BlueBtn } from "../public/BlueBtn";
import axios from "axios";
import Cookie from "../../util/Cookie";
import { useEffect, useRef, useState } from "react";

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
        align-items: center;
        gap: 0px;

        label {
            margin-right: 10px;
        }
    }

    .profile {
        display: flex;
        flex-direction: row;
        margin: 0 auto;
        
        div:nth-of-type(2) {
            width: 200px;
            display: flex;
            flex-direction: column;

            label {
                color: blue;
                cursor: pointer;
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

    button {
        margin-left: 40px;
        pointer-events: none;
        opacity: 0.4;
    }

    .active {
        pointer-events: auto;
        opacity: 1.0;
    }

    @media screen and (max-width: 770px) {
        margin-left: 10px;
        font-size: 13px;

        p {
            width: 130px;
        }
    }
`;

const EditProfile = ({ open, name, text, location, img, setName, setText, setLocation, setImg, oldName, handleReload }) => {
    const cookie = new Cookie();
    const [ file, setFile ] = useState(null);

    // 유효성 검사
    const [ isName, setIsName ] = useState(false);
    const [ isIntro, setIsIntro ] = useState(false);
    const [ isLocation, setIsLocation ] = useState(false);
    const btn = useRef(null);

    const editProfile = () => {
        axios({
            method: "patch",
            url: `${process.env.REACT_APP_API}/members/${cookie.get("memberId")}`,
            headers: { Authorization : cookie.get("authorization") },
            data : {
                userName: name,
                profileText: text,
                location: location
            }
        }).then(res => {
            handleReload();
        }).catch(e => {
            console.log(e);
        })
    };

    const onChangeName = () => {
        if(name.length < 2 || name.length > 6) {
            setIsName(true);
        }
        else setIsName(false);
    };

    const onChangeIntro = () => {
        if(text.length > 30) {
            setIsIntro(true);
        }
        else setIsIntro(false);
    };

    const onChangeLocation = () => {
        if(location.length > 10) {
            setIsLocation(true);
        }
        else setIsLocation(false);
    };

    const handleSubmit = () => {
        onChangeName();
        onChangeIntro();
        onChangeLocation();
        
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
                url: `${process.env.REACT_APP_API}/members/${cookie.get("memberId")}/profileimage`,
                data: formData,
                headers: { "Content-Type": "multipart/form-data", Authorization: cookie.get("authorization") }
            }).then(res => {
                setImg(res.data.profileImage);
            }).catch(e => {
                console.log(e);
            });
        }
    }, [file])

    useEffect(() => {
        if(name.length > 0) {
            onChangeName();
            onChangeIntro();
            onChangeLocation();
        }
    }, [name, text, location])

    useEffect(() => {
        if(!isName && !isIntro && !isLocation) {
            btn.current.classList.add("active");
        } else btn.current.classList.remove("active");
    }, [isName, isIntro, isLocation])

    return (
        <Wrapper>
            <div className="profile">
                <div className="profileImg">
                    <img src={img} alt="img"/>
                </div>
                <div>
                    <span>{oldName}</span>
                    <label htmlFor="file">프로필 사진 바꾸기</label>
                    <input onChange={updateImg} id="file" type="file" hidden />
                </div>
            </div>
            <div className={isName ? "red" : null}>
                <label htmlFor="username">별명</label>
                <input id="username" value={name} 
                    onChange={(e) => { 
                        setName(e.target.value)
                        onChangeName();
                    }}
                    />
                <p>별명은 한글이나 영문 2~6글자까지 가능합니다.</p>
            </div>
            <div className={isIntro ? "red" : null}>
                <label htmlFor="introduction">소개</label>
                <textarea id="introduction" value={text} 
                    onChange={(e) => {
                        setText(e.target.value);
                        onChangeIntro();
                    }}
                    />
                <p>소개는 0~30자까지 가능합니다.</p>
            </div>  
            <div className={isLocation ? "red" : null}>
                <label htmlFor="location">소속</label>
                <input id="location" value={location} 
                    onChange={(e) => {
                        setLocation(e.target.value);
                        onChangeLocation();
                    }}
                    />
                <p>소속은 0~10글까지 가능합니다.</p>
            </div>  
            <BlueBtn ref={btn} onClick={() => {
                handleSubmit();
            }}>변경하기</BlueBtn>
            
        </Wrapper>
    )
};

export default EditProfile;
