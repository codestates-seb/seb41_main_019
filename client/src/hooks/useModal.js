import { useState } from "react";
import Modal from "../components/public/Modal";

const useModal = (func) => {
    const [ isOpen, setIsOpen ] = useState(false);

    const open = () => {
        setIsOpen(true);
        document.body.style.overflow = "hidden";
    }
    
    const close = () => setIsOpen(false);

    return {
        Modal: isOpen ? ({ children }) => <Modal onClose={close} children={children} func={func} />: () => null, 
        open, 
        close
    };
};

export default useModal;