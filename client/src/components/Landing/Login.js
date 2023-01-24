import Logo from "../public/Logo"
import styled from "styled-components"
import { useEffect, useRef, useState } from "react"
import DefaultInput from "./DefaultInput"
import axios from "axios"
import Cookie from "../../util/Cookie"
import { decode } from "../../util/decode"
import { RiContactsBookLine } from "react-icons/ri"

const Wrapper = styled.div`
    position: absolute;
    width: 100%;
    height: 100%;
    padding: 85px 0px 0px 0px;
    display: flex;
    justify-content: center;
    align-items: center;

    form div:first-child {
        margin: 0px 0px 0px 0px;
    }
`
const StyledLogin = styled.div`
    display: inline-flex;
    flex-direction: column;
    align-items: center;
    padding: 30px 30px 60px 30px;
    border: 1px solid #dbdbdb;
    border-radius: 3px;
    margin: 0px 0px 50px 0px;
`

const StyledCheck = styled.div`
    width: 100%;
    display: flex;
    margin: 35px 0px 0px 0px;

    label {
        font-size: 14px;
    }
    
    label::before {
        display: inline-block;
        content: "";
        width: 16px;
        height: 16px;
        border: 1px solid #dbdbdb;
        margin: 0px 10px -4px 0px;
    }

    input:checked + label::before {
        content: "✔";
        width: 16px;
        height: 16px;
        text-align: center;
        color: white;
        background-color: #5E8B7E;
    }
`

const StyledBtn = styled.button`
    width: 100%;
    height: 40px;
    border: 1px solid #5E8B7E;
    background-color: #5E8B7E;
    border-radius: 30px;
    color: white;
    font-weight: 100;
    font-size: 14px;
    margin: 40px 0px 0px 0px;
    cursor: pointer;
`

const StyledBtn2 = styled(StyledBtn)`
    margin: 20px 0px 0px 0px;
    border: 1px solid #D96846;
    background-color : #D96846;
`

const Login = ({ setSelected, setIsLanded }) => {
    const [ id, setId ] = useState("");
    const [ pw, setPw ] = useState("");
    const inputRef = useRef([]);
    const cookie = new Cookie()

    useEffect(() => {
        inputRef.current[0].focus();
    }, [])

    const handleLogin = () => {
        axios({
            method: "post",
            url: "http://13.124.33.113:8080/member",
            data: {
                email: id,
                password: pw
            }
        }).then(res => {
            const date = new Date()
            const user = decode(res.headers.authorization);

            date.setMinutes(date.getMinutes() + 60);
            cookie.set("authorization", res.headers.authorization, { expires: date });
            cookie.set("memberId", user.memberId, { expires : date });
            cookie.set("username", user.username, { expires : date });

            date.setMinutes(date.getMinutes() + 60);
            cookie.set("refresh", res.headers.refresh, { expires: date });

            setIsLanded(false);
        })
        .catch(e => {
            //실패 처리
        })
    }

    return (
        <Wrapper>
            <StyledLogin>
                <Logo />
                <form onSubmit={() => false}>
                    <DefaultInput label="아이디" id="id" state={id} setState={setId} inputRef={inputRef} idx={0} 
                        autocomplete="off" />
                    <DefaultInput label="패스워드" id="password" type="password"
                        state={pw} setState={setPw} inputRef={inputRef} idx={1} />
                </form>
                <StyledCheck>
                    <input type="checkbox" id="saveId" hidden />
                    <label htmlFor="saveId">ID 저장하기</label>
                </StyledCheck>
                <StyledBtn onClick={handleLogin}>로그인</StyledBtn>
                <StyledBtn2 onClick={() => setSelected(2)}>회원가입</StyledBtn2>
            </StyledLogin>
        </Wrapper>
    )
}

export default Login;