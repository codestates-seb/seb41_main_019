import styled from "styled-components";
import { useEffect, useRef, useState } from "react";
import DefaultInput from "./DefaultInput";
import axios from "axios";
import { saveId } from "../../util/saveId";
import Cookie from "../../util/Cookie";
import logo from "../../assets/logo.png";

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

    img {
        height: 60px;
        margin: 0px 0px 30px 0px;
    }

    p {
        margin: 0px;
        font-size: 12px;
        color: red;
        padding: 2px 0px 0px 5px;
        visibility: hidden;
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

const Sign = ({ setSelected }) => {
    const [ name, setName ] = useState("");
    const [ id, setId ] = useState("");
    const [ pw, setPw ] = useState("");
    const [ rePw, setRePw ] = useState("");
    const [ checkName, setCheckName ] = useState(false);
    const [ checkId, setCheckId ] = useState(false);
    const [ checkPw, setCheckPw ] = useState(false);
    const [ checkRePw, setCheckRePw ] = useState(false);
    const inputRef = useRef([]);
    const cookie = new Cookie();

    useEffect(() => {
        inputRef.current[0].focus();
        
    },[])

    const handleSubmit = () => {
        if(!checkName) {
            inputRef.current[0].focus();
            return;
        }

        if(!checkId) {
            inputRef.current[1].focus();
            return;
        }

        if(!checkPw) {
            inputRef.current[2].focus();
            return;
        }

        if(!checkRePw) {
            inputRef.current[3].focus();
            return;
        }
        
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
            if(!cookie.get("id")) {
                saveId(id);
            } else {
                cookie.remove("id");
                saveId(id);
            }
        })
        .catch(e => {
            //?????? ??????
        });
    }

    const checkValue = (e, id) => {
        const p = e.target.parentNode.nextSibling;
        const input = e.target.value;

        if(id === "name") {
            const regexp = /^[???-???|a-z|A-Z|0-9|]+$/
            if(regexp.test(input) && 1 < input.length && input.length < 7) {
                p.style.color = "gray";
                setCheckName(true);
            } else  {
                p.style.color = "red";
                setCheckName(false);
            }
        }

        if(id === "id") {
            const regexp = /^[|a-z|A-Z|0-9|]+$/
            if(regexp.test(input) && 5 < input.length && input.length < 13) {
                p.style.color = "gray";
                setCheckId(true);
            } else {
                p.style.color = "red";
                setCheckId(false);
            }
        }

        if(id === "password") {
            const regexp = /^.*(?=^.{8,}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/
            if(regexp.test(input) && 7 < input.length && input.length) {
                p.style.color = "gray";
                setCheckPw(true);
            } else {
                p.style.color = "red";
                setCheckPw(false);
            }
        }

        if(id === "re-password" && checkPw) {
            if(pw.length < 1) {
                p.textContent = "??????????????? ??????????????????";
                return;
            }
            
            if(pw === input && pw.length > 0) {
                p.style.color = "gray";
                p.textContent = "??????????????? ???????????????.";
                setCheckRePw(true);
            } else {
                p.style.color = "red";
                p.textContent = "??????????????? ??????????????????.";
                setCheckRePw(false);
            }
        }
    }

    return (
        <Wrapper>
            <StyledLogin>
                <img src={logo} alt="img" />
                <form onSubmit={() => false}>
                    <DefaultInput label="?????????" id="name" state={name} setState={setName} checkValue={checkValue}
                    inputRef={inputRef} idx={0} handleSubmit={handleSubmit} />
                    <p>???????????? 2~6?????? ????????????????????????? ???????????????.</p>
                    <DefaultInput label="?????????" id="id" state={id} setState={setId} checkValue={checkValue} 
                    inputRef={inputRef} idx={1} handleSubmit={handleSubmit} />
                    <p>???????????? 6~12?????? ????????????????????????? ???????????????.</p>
                    <DefaultInput label="????????????" id="password" type="password" checkValue={checkValue}
                        state={pw} setState={setPw} inputRef={inputRef} idx={2} handleSubmit={handleSubmit} />
                    <p>??????????????? 8?????? ?????? ??????????????????????????????? ?????????????????????.</p>
                    <DefaultInput label="?????????" id="re-password" type="password" checkValue={checkValue}
                    state={rePw} setState={setRePw} inputRef={inputRef} idx={3} handleSubmit={handleSubmit} />
                    <p>??????????????? ??????????????????.</p>
                </form>
                <StyledBtn onClick={handleSubmit}>????????????</StyledBtn>
                <StyledBtn2 onClick={() => setSelected(1)}>?????? </StyledBtn2>
            </StyledLogin>
        </Wrapper>
    )
}

export default Sign;