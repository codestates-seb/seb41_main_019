import styled from "styled-components";
import EditProfile from "../components/Setting/EditProfile";
import DeleteProfile from "../components/Setting/DeleteProfile";
import { useState, useEffect } from "react";
import axios from "axios";
import Cookie from "../util/Cookie";
import defaultImg from "../assets/img/profile.jpg";
import Footer from "../components/public/Footer";
import useModal from "../hooks/useModal";
import { MdInfoOutline } from "react-icons/md";
import { useNavigate } from "react-router-dom";
import { RiUserSettingsLine } from "react-icons/ri";
import { RiUserUnfollowLine } from "react-icons/ri";

const Container = styled.div`
   
    .modal {
        display: flex;
        font-size: 17px;
        height: 80px;

        svg {
            font-size: 35px;
            
        }

        span {
            margin-left: 10px;
        }
    }
`;

const Wrapper = styled.div`
    display: flex;
    height: 500px;
    width: 800px;
    position: fixed;
    top:43%;
    left:50%;
    transform:translate(-50%, -50%);
    border: 1px solid #dbdbdb;

    input, textarea {
        width :150px;
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

    @media screen and (max-width: 770px) {
        top:50%;
        width: 450px;
        border: 0;
    }
`;

const StyledMenu = styled.div`
    display: flex;
    width: 30%;
    gap: 20px;
    flex-direction: column;
    border-right: 1px solid #dbdbdb;

    .active {
        border-left: 3px solid #374435;
    }

    div {
        display: flex;
        align-items: center;
        padding: 20px;

        svg {
            font-size: 20px;
            margin-right: 10px;
        }

        p {
            margin: 0;
        }

        @media screen and (max-width: 770px) {
            p {
                display: none;
            }
        }
    }

    
`

const Setting = ({ setIsLanded }) => {
    const [ isClicked, setIsClicked ] = useState(0);
    const [ name, setName ] = useState("");
    const [ text, setText ] = useState("");
    const [ location, setLocation] = useState("");
    const [ img, setImg ] = useState(null);
    const { open, close, Modal } = useModal(isClicked === 1 && deleteOut);
    const [ oldName, setOldName ] = useState("");
    const [ reload, setReload ] = useState(false);
    const cookie = new Cookie();
    const navigate = useNavigate();

    const handleReload = () => setReload(!reload);

    function deleteOut () {
        cookie.remove("memberId");
        cookie.remove("list");
        cookie.remove("username");
        cookie.remove("refresh");   
        cookie.remove("authorization");
        navigate("/");
        setIsLanded(true);
    }

    useEffect(() => {
        axios({
            method: "get",
            url: `${process.env.REACT_APP_API}/members/${cookie.get("memberId")}`,
            headers: { Authorization : cookie.get("authorization") }
        }).then(res => {
            const user = res.data.data;
            setName(user.userName);
            setText(user.profileText);
            setLocation(user.location);
            setOldName(user.userName);
            user.profileImage ? setImg(user.profileImage) : setImg(defaultImg);
        }).catch(e => {
            console.log(e);
        })
    }, [reload])

    return (
        <Container>
            <Wrapper>
                <StyledMenu>  
                    <div className={isClicked === 0 ? "active" : null} onClick={() => setIsClicked(0)}>
                        <RiUserSettingsLine />
                        <p>프로필 편집</p>
                    </div>
                    <div className={isClicked === 1 ? "active" : null} onClick={() => setIsClicked(1)}>
                        <RiUserUnfollowLine />
                        <p>계정 탈퇴</p>
                    </div>
                </StyledMenu>
                { isClicked === 0 
                    ? <EditProfile open={open} name={name} text={text} location={location} img={img} oldName={oldName}
                    handleReload={handleReload} setName={setName} setText={setText} setLocation={setLocation} setImg={setImg} /> 
                    :  <DeleteProfile open={open} name={name} img={img} setIsLanded={setIsLanded}/>
                }
            </Wrapper>
            <Footer />
            <Modal>
                {
                    isClicked === 0 && (
                        <div className="modal">
                            <MdInfoOutline />
                            <span>변경되었습니다</span>
                        </div>
                    ) 
                }   
                {   
                    isClicked === 1 && (
                        <div className="modal">
                            <MdInfoOutline />
                            <span>탈퇴되었습니다</span>
                        </div>
                    )
                }
            </Modal>
        </Container>
    )
};

export default Setting;