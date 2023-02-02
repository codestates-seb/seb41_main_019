import styled from "styled-components";
import { BsSearch } from "react-icons/bs";
import { useState } from "react";

const StyledSearch = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;

  label {
    font-size: 18px;
    font-weight: 600;
    margin: 0px 0px 10px 0px;
  }

  div {
    background-color: #d9d9d9;
    border-radius: 10px;
    height: 30px;
    display: flex;
    align-items: center;

    svg {
      margin: 0px 10px 0px 11px;
    }

    input {
      width: 100%;
      background-color: #d9d9d9;
      font-size: 12px;
      padding: 2px 0px 0px 0px;
      border: 0px;
      outline: none;
    }
  }

  .isFocused {
    box-shadow: 0 0 6px #5e8b7e;
  }
`;

const Input = ({ label, open, input }) => {
  const [isFocused, setIsFocused] = useState(false);

  return (
    <StyledSearch>
      <label>{label}</label>
      <div className={isFocused ? "isFocused" : ""}>
        <BsSearch />
        <input
          id={label}
          type="text"
          ref={input}
          placeholder="Search..."
          onFocus={() => setIsFocused(true)}
          onBlur={() => setIsFocused(false)}
          onKeyUp={(e) => {
            if(e.key === "Enter") {
              e.target.value = "";
              open && open();
            }
          }}
        ></input>
      </div>
    </StyledSearch>
  );
};

export default Input;
