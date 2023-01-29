import Logo from "../public/Logo"
import styled from "styled-components"
import { useEffect, useRef, useState } from "react"
import DefaultInput from "./DefaultInput"
import axios from "axios"

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

const Sign = ({ setSelected }) => {
    const [ name, setName ] = useState("");
    const [ id, setId ] = useState("");
    const [ pw, setPw ] = useState("");
    const [ rePw, setRePw ] = useState("");
    const inputRef = useRef([]);

    useEffect(() => {
        inputRef.current[0].focus();
    },[])

    const handleSubmit = () => {
        axios({
            method: "post",
            url: `${process.env.REACT_APP_API}/members/sign-up`,
            data: {
                "userName" : name,
                "email" : id,
                "password" : rePw
            }
        }).then(res => {
            setSelected(1);
        })
        .catch(e => {
            //실패 처리
        });
    }

    return (
        <Wrapper>
            <StyledLogin>
                <Logo />
                <form onSubmit={() => false}>
                    <DefaultInput label="닉네임" id="name" state={name} setState={setName} inputRef={inputRef} idx={0}/>
                    <DefaultInput label="아이디" id="id" state={id} setState={setId} inputRef={inputRef} idx={1}/>
                    <DefaultInput label="패스워드" id="password" type="password"
                        state={pw} setState={setPw} inputRef={inputRef} idx={2} />
                    <DefaultInput label="재확인" id="re-password" type="password"
                    state={rePw} setState={setRePw} inputRef={inputRef} idx={3} />
                </form>
                <StyledBtn onClick={handleSubmit}>회원가입</StyledBtn>
                <StyledBtn2 onClick={() => setSelected(1)}>취소 </StyledBtn2>
            </StyledLogin>
        </Wrapper>
    )
}

export default Sign;