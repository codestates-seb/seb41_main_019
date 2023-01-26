export const exchangeTime = (post) => {
    //일 차이 계산
    const nDay = new Date().toISOString().slice(0, 10).split("-")[2];
    const pDay = post.createdAt.slice(0, 10).split("-")[2];

    const day = (nDay - pDay) * 24 * 60 * 60;

    //시간 차이 계산
    const nTime = new Date().toISOString().slice(11, 19).split(":").map((el, idx) => {
        switch(idx) {
            case 0 : return Number(el) * 60 * 60;
            case 1 : return Number(el) * 60;
            case 2 : return Number(el);
            default : break;
        }
    }).reduce((acc, cur) => acc + cur);

    const pTime = post.createdAt.slice(11, 19).split(":").map((el, idx) => {
        switch(idx) {
            case 0 : return Number(el) * 60 * 60;
            case 1 : return Number(el) * 60;
            case 2 : return Number(el);
            default : break;
        }
    }).reduce((acc, cur) => acc + cur);

    // 일 차이 반영
    const differ = (nTime + 9 * 60 * 60) - pTime + day;

    // 최대 시간 차로 변환
    const hour = parseInt(differ / 60 / 60);
    const minute = parseInt(differ / 60 % 60);
    const second = differ % 60;

    if(hour > 24) {
        return `${parseInt(hour/24)}일 전`;
    }else if(hour > 0) {
        return `${hour}시간 전`;
    } else if(minute > 0) {
        return `${minute}분 전`;
    } else {
        return `${second} 초 전`;
    }
}