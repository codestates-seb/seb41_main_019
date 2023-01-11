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
    border-radius: 5px;
    padding: 10px 0px 10px 10px;
    display: flex;
    align-items: center;

    svg {
      margin: 0px 10px 4px 0px;
    }

    input {
      border: 0px;
      background-color: #d9d9d9;
      height: 80%;
      font-size: 16px;
      outline: none;
    }
  }

  .isFocused {
    box-shadow: 0 0 0 1px #2f4858 inset;
  }
`;

const Search = ({ label }) => {
  const [isFocused, setIsFocused] = useState(false);

  return (
    <StyledSearch>
      <label>{label}</label>
      <div className={isFocused ? "isFocused" : ""}>
        <BsSearch />
        <input
          id={label}
          type="text"
          placeholder="Search..."
          onFocus={() => setIsFocused(true)}
          onBlur={() => setIsFocused(false)}
        ></input>
      </div>
    </StyledSearch>
  );
};

export default Search;
