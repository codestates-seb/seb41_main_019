import styled from "styled-components";
import { BsSearch } from "react-icons/bs";

const StyledSearch = styled.div`
  display: flex;
  flex-direction: column;

  label {
    font-size: 18px;
    font-weight: 600;
    padding: 0px 0px 0px 15px;
  }

  div svg {
    position: relative;
    left: 25px;
    top: 3px;
  }

  div input {
    padding: 5px 10px 5px 30px;
  }
`;

const Search = ({ label }) => {
  return (
    <StyledSearch>
      <label>{label}</label>
      <div>
        <BsSearch />
        <input id={label} type="text" placeholder="Search..."></input>
      </div>
    </StyledSearch>
  );
};

export default Search;
