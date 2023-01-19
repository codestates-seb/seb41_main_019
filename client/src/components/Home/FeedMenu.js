import styled from "styled-components";

const Wrapper = styled.div`
    position: absolute;
    top: 75px;
    right: 1px;
    display: flex;
    flex-direction: column;
    width: 100px;
    height: 100px;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
    box-shadow: 5px 5px 10px 1px rgba(0,0,0,.3);
    z-index: 100;

    button {
        height: 100%;
        background-color: white;
        border: 0;
        border-bottom: 1px solid #dbdbdb;
        font-size: 14px;
        cursor: pointer;

        :hover {
            opacity: 1;
        }
    }
`;

const FeedMenu = ({ handleDelete, handleMenu, handleEdit }) => {
    return (
        <Wrapper>
            <button 
                onClick={() => { 
                    handleEdit(); 
                    handleMenu();
                }}>
                Edit
            </button>
            <button 
                onClick={() => {
                    handleDelete(); 
                    handleMenu();
                    }}>
                Delete
            </button>
            <button onClick={handleMenu}>Cancel</button>
        </Wrapper>
    )
};

export default FeedMenu;