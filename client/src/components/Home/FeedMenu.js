import styled from "styled-components";

const Wrapper = styled.div`
    position: absolute;
    right: 350px;
    display: flex;
    flex-direction: column;
    width: 150px;
    height: 200px;
    z-index: 1000;
    border: 1px solid #dbdbdb;
    border-radius: 5px;
    box-shadow: 5px 5px 10px 1px rgba(0,0,0,.3);

    button {
        height: 100%;
        background-color: white;
        opacity: 0.5;
        border: 0;
        border-bottom: 1px solid #dbdbdb;
        font-size: 17px;
        cursor: pointer;

        :hover {
            opacity: 1;
        }
    }
`;

const FeedMenu = ({ handleDelete, handleMenu }) => {
    return (
        <Wrapper>
            <button>Edit</button>
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