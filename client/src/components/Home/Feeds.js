import styled from "styled-components";
import Feed from "./Feed";

const Wrapper = styled.section`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
`

const Feeds = ({ handleModal }) => { 
    const datas = new Array(10).fill(0);

    return (
        <Wrapper>
            {
                datas.map(data => <Feed handleModal={handleModal} />)
            }
        </Wrapper>
    )
}

export default Feeds;