import { useEffect, useRef } from "react";
import styled from "styled-components";
import Input from "../Input";

const StyledSearch = styled.div`
  display: flex;
  flex-direction: column;
  width: 350px;
  height: 100%;
  padding: 21px 20px 0px 20px;
  gap: 20px;

  > p {
    font-size: 18px;
    font-weight: 600;
    margin: 0px;
  }

  > p::before {
    content: "";
    display: block;
    width: 400px;
    margin: 0px 0px 20px -20px;
    border-top: 1px solid #dbdbdb;
  }

  > .chat-area::before {
    content: "";
    display: block;
    width: 400px;
    margin: 0px 0px 20px -20px;
    border-top: 1px solid #dbdbdb;
  }
`;

const Search = ({ open }) => {
    const input = useRef(null);

    useEffect(() => {
      input.current.focus();
    }, [])

    return (
        <StyledSearch>
            <Input label="검색" open={open} input={input}/>
        </StyledSearch>
    );
}

export default Search;