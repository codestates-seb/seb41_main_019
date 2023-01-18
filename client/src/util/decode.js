import jwt_decode from "jwt-decode";

export const decode = (token) => {
    const user = jwt_decode(token);

    return { memberId : user.memberId, username : user.username };
}