import styled from "styled-components";
import Feed from "./Feed";

const Wrapper = styled.div`
    display: flex;
    justify-content: center;
`

const Feeds = () => {
    return (
        <Wrapper>
            <Feed />
        </Wrapper>
    )
}

export default Feeds;