import styled from "styled-components";
import CloseBtn from "../CloseBtn";

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

        svg {
            font-size: 15px;
        }
    }
`;

const Tags = ({ tags, addTags, removeTag, tagRef }) => {
    return (
        <Wrapper className="tags">
            <ul>
                {
                    tags.map((tags,idx) => {
                        return <li key={idx}>{tags}<CloseBtn onClick={removeTag} ref={tagRef} /></li>
                    })
                }
            </ul>
            <input onKeyUp={addTags} placeholder="# 키워드"></input>
        </Wrapper>
    )
};

export default Tags;