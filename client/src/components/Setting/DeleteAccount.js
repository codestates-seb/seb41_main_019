import styled from "styled-components"

const Wrapper = styled.div`

    div {
        display: flex;
    }

    .profileImg {
        width: 50px;
        height: 50px;
        background-color: gray;
        border-radius: 50px;
    }

    button {
        background-color: #D96846;
        width: 100px;
        height: 40px;
        border: 0px;
        border-radius: 5px;
        cursor: pointer;
        margin-right: 10px;
        box-shadow: 1px 3px 8px -2px rgb(90, 90, 90);
        color: white;
    }
`;

const DeleteAccount = () => {
    return (
        <Wrapper>
            <div>
                <div className="profileImg"></div>
                <span>user1</span>
            </div>
            <p>정말 계정을 삭제하시겠습니까? </p>
            <div>
                <label htmlFor="confirmPw">계속하려면 비밀번호를 입력해주세요</label>
                <input id="confirmPw" />
            </div>
            <button>계정 삭제</button>
        </Wrapper>
    )
};

export default DeleteAccount;