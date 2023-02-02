export const exchangeTime = (post) => {
    const now = new Date().getTime();
    const pTime = new Date(post.createdAt).getTime();
    const differ = now - pTime;

    const difD = parseInt(differ / 24 / 60 / 60 / 1000);
    const difT = parseInt(differ / 60 / 60 / 1000);
    const difM = parseInt(differ / 60 / 1000);
    const difS = parseInt(differ / 1000);

    if(difD > 0) return `${difD}일 전`;
    if(difT > 0) return `${difT}시간 전`;
    if(difM > 0) return `${difM}분 전`;
    if(difS > 0) return `${difS}초 전`; 
}