import axios from "axios"
import Cookie from "./Cookie";

export const followed = (type = true, memberId, handleChange) => {
    const cookie = new Cookie();

    if(type) {
        axios({
            method: "post",
            url: `${process.env.REACT_APP_API}/followed/${memberId}`,
            headers: { Authorization: cookie.get("authorization") }
        }).then(res => {
            handleChange();
        }).catch(e => {
            console.log(e);
        });
    } else {
        axios({ 
            method: "delete", 
            url: `${process.env.REACT_APP_API}/followed/${memberId}`,
            headers: { Authorization: cookie.get("authorization") }
        }).then(res => {
            handleChange();
        }).catch(e => {
            console.log(e);
        });
    }

    return;
};