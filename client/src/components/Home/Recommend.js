import styled from "styled-components";

const StyledLi = styled.li`
    display: flex;
    flex-direction: column;
    align-items: center;

    img {
        width: 88px;
        height: 88px;
        padding: 5px;
        border-radius: 50px;
        cursor: pointer;
    }

    span {
        font-size: 12px;
        letter-spacing: 1px;
    }
`;

const Recommend = ({ el, idx }) => {
    return (
        <StyledLi key={idx}>
            <img src={el.img} alt="img" />
            <span>{el.user_name}</span>
        </StyledLi>
    )
};

export default Recommend;