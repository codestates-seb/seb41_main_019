import axios from "axios"
import Cookie from "./Cookie"

export const findUser = (id) => {
    const cookie = new Cookie();

    return axios({
        method: "get",
        url: `${process.env.REACT_APP_API}/members/${id}`,
        headers: { Authorization: cookie.get("authorization") }
    }).then(res => {
        return res.data.data;
    }).catch(e => {
        //에러 처리
    })
}