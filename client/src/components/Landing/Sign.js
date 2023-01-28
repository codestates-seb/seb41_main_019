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

    //오류메시지
    const [nameMessage, setNameMessage] = useState('');
    const [IdMessage, setIdMessage] = useState('');
    const [pwMessage, setPwMessage] = useState('');
    const [pwConfirmMessage, setPwConfirmMessage] = useState('');

    //유효성 검사
    const [isName, setIsName] = useState(false)
    const [isId, setIsId] = useState(false)
    const [isPw, setIsPw] = useState(false)
    const [isPwConfirm, setIsPwConfirm] = useState(false)

    useEffect(() => {
        inputRef.current[0].focus();
    },[])

    const handleSubmit = () => {
        axios({
            method: "post",
            url: "http://13.124.33.113:8080/members/sign-up",
            data: {
                "userName" : name,
                "email" : id,
                "password" : rePw
            }
        }).then(res => {
            setSelected(1);
        })
        .catch(e => {
            console.log(e);
        });
    }

    const onChangeName = (e) => {
        setName(e.target.value);
        if(e.target.value.length < 2 || e.target.value.length > 5) {
            setNameMessage('2글자 이상 7글자 미만으로 입력해주세요.');
            setIsName(false);
        } else {
            setNameMessage('올바른 닉네임입니다.');
        }
    };

    const onChangeId = (e) => {
        const idRegex = /^[a-zA-Z0-9]*$/
        const idCurrent = e.target.value;
        setId(idCurrent);

        if (!idRegex.test(idCurrent)) {
            setIdMessage('아이디 형식이 틀렸어요! 다시 확인해주세요!');
            setIsId(false);
        } else {
            setIdMessage('올바른 이메일 형식이에요 : )');
            setIsId(true);
        }
    };
    

    const onChangePw = (e) => {
        const pwRegex = /^(?=.[A-Za-z])(?=.\d)(?=.[@$!%#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
        const pwCurrent = e.target.value;
        setPw(pwCurrent);

        if(!pwRegex.test(pwCurrent)) {
            setPwMessage('영문자+숫자+특수문자 조합으로 8자 이상 입력해주세요!');
            setIsPw(false);
        } else {
            setPwMessage('안전한 비밀번호입니다');
            setIsPw(true);
        }
    };

    const onChangePwConfirm = (e) => {
        const pwConfirmCurrent = e.target.value;
        setRePw(pwConfirmCurrent);

        if(pw === pwConfirmCurrent) {
            setPwConfirmMessage('비밀번호를 똑같이 입력했습니다');
            setIsPwConfirm(true);
        } else {
            setPwConfirmMessage('비밀번호가 다릅니다. 다시 확인해주세요')
            setIsPwConfirm(false);
        }
    }

    return (
        <Wrapper>
            <StyledLogin>
                <Logo />
                <form onSubmit={() => false}>
                    <DefaultInput label="닉네임" id="name" state={name} setState={setName} 
                    inputRef={inputRef} idx={0} onChange={onChangeName} className={`${isName ? 'correct' : 'wrong'}`}/>
                    { name.length > 0 && <span className={`message ${isName ? 'success' : 'error'}`}>{nameMessage}</span> }
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