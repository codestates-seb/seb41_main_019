import styled from "styled-components";
import CloseBtn from "../CloseBtn";

const Wrapper = styled.div`
    display: flex;
    flex-direction: column;
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
        flex-wrap: wrap;
        margin: 0;
        padding: 0;
        list-style: none;
    }

    ul li {
        display: flex;
        align-items: center;
        background-color: #A7C4BC;
        border-radius: 30px;
        padding: 6px 10px 6px 10px;
        font-size: 14px;
        color: white;
        cursor: pointer;
        margin: 10px 5px 5px 0px;
        white-space: nowrap;

        svg {
            margin-left: 5px;
            font-size: 15px;
            background-color: rgba(255, 255, 255, 0.3);
            border-radius: 30px;
            color: gray;
            padding: 2px;
        }
    }
`;

const Tags = ({ tags, addTags, removeTags, edit }) => {
    return (
        <Wrapper>
            <input onKeyUp={addTags} placeholder="# 키워드"></input>
            <ul>
                {
                    tags.map((tag,idx) => {
                        return <li key={idx} id={idx}>{edit ? tag.tagName : tag}<CloseBtn handleEvent={removeTags} /></li>
                    })
                }
            </ul>
        </Wrapper>
    )
};

export default Tags;