import { AiOutlineClose } from "react-icons/ai";

const CloseBtn = ({ handleModal }) => {
    return (
        <div>
            <AiOutlineClose onClick={() => handleModal()} />
        </div>
    )
};

export default CloseBtn;