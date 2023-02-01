import axios from "axios"
import Cookie from "../util/Cookie";

export const follow = (type = true, memberId, handleChange) => {
    const cookie = new Cookie();

    if(type) {
        axios({
            method: "post",
            url: `${process.env.REACT_APP_API}/followings/${memberId}`,
            headers: { Authorization: cookie.get("authorization") }
        }).then(res => {
            handleChange();
        }).catch(e => {
            console.log(e);
        });
    } else {
        axios({ 
            method: "delete", 
            url: `${process.env.REACT_APP_API}/followings/${memberId}`,
            headers: { Authorization: cookie.get("authorization") }
        }).then(res => {
            handleChange();
        }).catch(e => {
            console.log(e);
        });
    }

    return;
};