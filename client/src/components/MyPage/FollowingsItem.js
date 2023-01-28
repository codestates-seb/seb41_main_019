import styled from "styled-components";

const StyledFollowingsItem = styled.li`
  display: flex;
  padding: 3px 5px 2px 5px;
  margin: 0px 0px 10px 0px;
  align-items: center;
  cursor: pointer;

  div:nth-of-type(1) {
    width: 10%;
    margin: 0px 10px 0px 0px;

    img {
      width: 24px;
      height: 24px;
      border-radius: 30px;
    }
  }

  div:nth-of-type(2) {
    width: 81%;
    display: flex;
    flex-direction: column;

    span {
      font-size: 14px;
      font-weight: 300;
      color: #5e8b7e;
    }

    span:last-child {
      font-size: 12px;
      color: black;
    }
  }

  /* :hover {
    ${({ top }) => !top ? "background-color: #DBDBDB;" : null}
    ${({ top }) => !top ? "border-radius: 3px;" : null}
  }

  :hover button {
    ${({ top }) => !top ? "background-color: #DBDBDB;" : null}
  }

  .top {
    margin-right: -20px;
  } */
`;

const FollowingsItem = ({following}) => {
    return (
        <StyledFollowingsItem>
            <div>
                <img
                src={following.profileImage}
                alt="img"
                />
            </div>
            <div>
                <span>{following.userName}</span>
                <span>{following.profileText}</span>
            </div>
        </StyledFollowingsItem>
    )
}

export default FollowingsItem