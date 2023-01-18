import styled from "styled-components";
import Post from "./Post";
import { ReactComponent as NoContent } from "../../assets/svg/monstera.svg";

const Wrapper = styled.section`
    position: relative;
    display: flex;
    width: 100%;
    flex-direction: column;
    align-items: center;
    justify-content: center;

    .no-content {
        display: flex;
        flex-direction: column;
        align-items: center;
        position: fixed;
        top: 40%;
        width: 300px;
        height: 300px;
        opacity: 0.3; 
    }
`

const Feed = ({ handleModal, handleDelete }) => { 
    const datas = new Array(0).fill(0);

    return (
        <Wrapper>
            { datas.length < 1 
                ?
                    <div className="no-content"> 
                        <NoContent />
                        <span>게시물이 없습니다.</span> 
                    </div>
                : datas.map((data,idx) => <Post key={idx} handleModal={handleModal} handleDelete={handleDelete} />)
            }
        </Wrapper>
    )
}

export default Feed;