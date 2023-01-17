import styled from "styled-components";
import Feed from "./Feed";

const Wrapper = styled.section`
    display: flex;
    width: 100%;
    flex-direction: column;
    align-items: center;
    justify-content: center;
`

const Feeds = ({ handleModal, handleDelete }) => { 
    const datas = new Array(10).fill(0);

    return (
        <Wrapper>
            {
                datas.map((data,idx) => <Feed key={idx} handleModal={handleModal} handleDelete={handleDelete} />)
            }
        </Wrapper>
    )
}

export default Feeds;