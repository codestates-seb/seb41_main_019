import styled from "styled-components";

const StyledInput = styled.div`
    display: flex;
    flex-direction: column;
    margin: 20px 0px 0px 0px;

    label {
        font-size: 14px;
        color: gray;
        position: relative;
        top: 26px;
        padding: 0px 0px 0px 6px;
        cursor: text;
        transition: top 0.2s ease;
    }

    :focus-within label{
        top: -4px;
    }

    input { 
        height: 35px;
        width: 280px;
        border: 1px solid #dbdbdb;
        border-radius: 3px;
        outline: none;
        padding: 0px 0px 0px 6px;

        &:focus {
            &.wrong {
                outline: 1px solid red;
                box-shadow: 3px 3px 15px rgb(197 110 110), -3px -3px 15px rgb(197 110 110);
            }
            &.correct {
                outline: 1px solid #6bbbf7;
            }
        }
    }

    .valid {
        top: -4px;
    }
`

const DefaultInput = ({ label, id, type = "text", state, setState, inputRef, idx }) => {
    return (
        <StyledInput>
            <label htmlFor={id} className={state.length > 0 ? "valid" : ""}>{label}</label>
            <input id={id} type={type} value={state}
                onChange={(e) => setState(e.target.value)} ref={(el) => inputRef.current[idx] = el}
                autoComplete={id === "password" ? "off" : "on"}/>
        </StyledInput>
    )
}

export default DefaultInput;