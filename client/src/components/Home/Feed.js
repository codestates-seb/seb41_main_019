import styled from "styled-components";
import a from "../../assets/img/plant/11.jpg";
import b from "../../assets/img/plant/14.png";
import { AiOutlineHeart, AiOutlineShareAlt, AiOutlineMessage } from "react-icons/ai";
import { BsBookmarkPlus } from "react-icons/bs";
import { FiUserPlus } from "react-icons/fi";

const Wrapper = styled.div`
    width: 500px;
    height: 824px;

    img {
        width: 100%;
        height: 500px;
    }
`;

const StyledHeader = styled.section`

    img {
        width: 50px;
        height: 50px;
    }
`;

const StyledInteraction = styled.section`

`;

const Feed = () => {
    return (
        <Wrapper>
            <StyledHeader>
                <img src={a} alt="img" />
                <span>홍길동</span>
                <span>7시간 전</span>
                <FiUserPlus />
                <AiOutlineMessage />
            </StyledHeader>
            <img src={b} alt="img" />
            <StyledInteraction>
                <AiOutlineHeart />
                <AiOutlineShareAlt />
                <BsBookmarkPlus />
                <p>좋아요 700개</p>
                <p>제가 키우는 몬스테라 알보에요!</p>
                <span>user1</span>
                <span>너무 예뻐요!!</span>
                <p>댓글 더보기 및 댓글쓰기</p>
            </StyledInteraction>
        </Wrapper>
    );
}

export default Feed;