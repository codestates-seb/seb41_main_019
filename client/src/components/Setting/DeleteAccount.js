import styled from "styled-components"

const Wrapper = styled.div`
    width: 100%;
    display:flex;
    flex-direction: column;
    align-items: center;
    gap: 30px;
    margin-top: 50px;

    div {
        display: flex;
        flex-direction: column;
        gap: 20px;
    }

    div:first-of-type{
        display: flex;
        flex-direction: row;
        align-items: center;
        gap: 30px;

        .profileImg {
            width: 50px;
            height: 50px;
            background-color: gray;
            border-radius: 50px;
        }
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
            <div>
                <label htmlFor="confirmPw">계속하려면 비밀번호를 입력해주세요</label>
                <input id="confirmPw" />
            </div>
            <button>계정 삭제</button>
        </Wrapper>
    )
};

export default DeleteAccount;