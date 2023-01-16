import Logo from "../public/Logo"
import styled from "styled-components"

const Wrapper = styled.div`
    position: absolute;
    width: 100%;
    height: 100%;
    padding: 85px 0px 0px 0px;
    display: flex;
    justify-content: center;
    align-items: center;
`
const StyledLogin = styled.div`
    display: inline-flex;
    flex-direction: column;
    gap: 20px;
`

const StyledInput = styled.div`
    label {
        font-size: 14px;
        color: gray;
    }
    input {
        height: 35px;
        border: 1px solid #dbdbdb;
        border-radius: 3px;
        outline: none;
    }
`

const Login = () => {
    return (
        <Wrapper>
            <StyledLogin>
                <Logo />
                <StyledInput>
                    <label>아이디</label>
                    <input />
                </StyledInput>
                <StyledInput>
                    <label>패스워드</label>
                    <input />
                </StyledInput>
            </StyledLogin>
        </Wrapper>
    )
}

export default Login