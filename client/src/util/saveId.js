import Cookie from "./Cookie"

export const saveId = (id) => {
    const cookie = new Cookie();
    const date = new Date();
    date.setMinutes(date.getMinutes() + 60 * 24 * 7);

    if(!cookie.get("id")) cookie.set("id", id, { expires: date });
}

export const loadId = () => {
    const id = new Cookie().get("id");

    return id ? id : "";
}

export const deleteId = () => {
    const cookie = new Cookie();

    if(cookie.get("id")) cookie.remove("id");
}