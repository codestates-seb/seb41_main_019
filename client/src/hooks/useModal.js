import { useState } from "react";
import Modal from "../components/public/Modal";

const useModal = () => {
    const [ isOpen, setIsOpen ] = useState(false);

    const open = () => {
        setIsOpen(true);
        document.body.style.overflow = "hidden";
    }
    const close = () => setIsOpen(false);

    return {
        Modal: isOpen ? () => <Modal onClose={close} /> : () => null, 
        open, 
        close
    };
};

export default useModal;