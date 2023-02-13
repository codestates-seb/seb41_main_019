import styled from "styled-components";

const StyledInput = styled.div`
    display: flex;
    flex-direction: column;
    margin: 10px 0px 0px 0px;

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
    }
    .valid {
        top: -4px;
    }
`

const DefaultInput = ({ label, id, type = "text", state, setState, inputRef, idx, handleLogin, checkValue, handleSubmit }) => {

    const viewHelp = (e) => {
        try {
            const p = e.target.parentNode.nextSibling;
            if(!p.style.visibility) p.style.visibility = "visible";
            else p.style.visibility = null;
        }catch {}
    }

    return (
        <StyledInput>
            <label htmlFor={id} className={state.length > 0 ? "valid" : ""}>{label}</label>
            <input id={id} type={type} value={state}
                onChange={(e) => {
                    setState(e.target.value);
                    checkValue && checkValue(e, id);
                }} ref={(el) => inputRef.current[idx] = el}
                onKeyDown={(e) => {
                    if(e.key === "Enter") {
                        if(!handleLogin) {
                            handleSubmit();
                        } else handleLogin();
                    }
                }}
                onFocus={viewHelp}
                onBlur={viewHelp}
                autoComplete="off" />
        </StyledInput>
    )
}

export default DefaultInput;