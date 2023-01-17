import styled from "styled-components";
import Post from "./Post";

const Wrapper = styled.section`
    display: flex;
    width: 100%;
    flex-direction: column;
    align-items: center;
    justify-content: center;
`

const Feed = ({ handleModal, handleDelete }) => { 
    const datas = new Array(10).fill(0);

    return (
        <Wrapper>
            {
                datas.map((data,idx) => <Post key={idx} handleModal={handleModal} handleDelete={handleDelete} />)
            }
        </Wrapper>
    )
}

export default Feed;