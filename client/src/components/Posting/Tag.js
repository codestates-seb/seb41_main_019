import styled from "styled-components";

const Wrapper = styled.div`
    display: flex;
    ul {
        display: flex;
        margin: 0;
        padding: 0;
        list-style: none;
    }

    ul li {
        width: 200px;
        height: 40px;
        border: 1px solid #dbdbdb;
        border-radius: 5px;
        cursor: pointer;
        padding: 20px;
    }
`;

const StyledTag = styled.input`
    width: 100px;
    height: 40px;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
    cursor: pointer;
    padding: 20px;
`;

const Tag = () => {

    const handleEnter = (e) => {
        if(e.key === 'Enter'){
            return <StyledTag>{e.target.value}</StyledTag>
        }
    } 

    const tags = ["몬스테라 알보", "식테크"];

    return (
        <Wrapper>
            <StyledTag placeholder="# 키워드" onKeyUp={handleEnter}/>
            <ul>
            {
                tags.map((el,idx) => {
                    return (
                            <li key={idx}>{el}</li>
                    )
                })
            }
            </ul>
        </Wrapper>
    )
};

export default Tag;