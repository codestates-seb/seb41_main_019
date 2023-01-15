import styled from "styled-components";
import Feed from "./Feed";

const Wrapper = styled.section`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
`

const Feeds = ({ handleModal, handleMenu }) => { 
    const datas = new Array(10).fill(0);

    return (
        <Wrapper>
            {
                datas.map((data,idx) => <Feed key={idx} handleModal={handleModal} handleMenu={handleMenu} />)
            }
        </Wrapper>
    )
}

export default Feeds;