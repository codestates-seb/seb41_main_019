import styled from "styled-components";
import Feed from "./Feed";

const Wrapper = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
`

const Feeds = () => { 
    return (
        <Wrapper>
            <Feed />
            <Feed />
            <Feed />
            <Feed />
            <Feed />
            <Feed />
            <Feed />
            <Feed />
            <Feed />
            <Feed />
        </Wrapper>
    )
}

export default Feeds;