import styled from "styled-components";
import { useState } from "react";

const Wrapper = styled.div`
    display: flex;
    border: 1px solid #dbdbdb;
    padding: 10px 10px;
    width: 100%;

    input {
        display: inline-block;
        width: 100%;
        height: 40px;
        border: 1px solid #dbdbdb;
        border-radius: 5px;
        cursor: pointer;
        padding: 10px;
        outline: none;

        :focus {
            box-shadow: 0 0 6px #5e8b7e;
        }
    }

    ul {
        display: flex;
        margin: 0;
        padding: 0;
        list-style: none;
    }

    ul li {
        display: flex;
        align-items: center;
        border: 1px solid #dbdbdb;
        border-radius: 5px;
        padding: 0px 10px 0px 10px;
        cursor: pointer;
        margin: 0px 5px 0px 0px;
        white-space: nowrap;
    }
`;

const Tag = () => {
    const [tags, setTags] = useState([]);

    const tagList = tags.map((tag,idx) => {
        return (
            <li key={idx}>{tag}</li>
        )
    });

    const addTags = (e) => {
        if(e.key === "Enter" && e.target.value.length > 0) {
            setTags([...tags, e.target.value]);
            e.target.value = "";
        }
        deleteTags(e);
    };

    const deleteTags = (e) => {
        if(e.key === "Backspace") {
            setTags(tags.slice(0, tags.length - 1));
        }
    };

    return (
        <Wrapper className="tags">
            <ul>{tagList}</ul>
            <input onKeyUp={addTags} placeholder="# 키워드"></input>
        </Wrapper>
    )
};

export default Tag;