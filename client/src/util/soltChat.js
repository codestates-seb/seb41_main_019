export const soltChat = (log) => {
    const solted = [];
  
    log.forEach(msg => {
        if(!solted[0]) solted.push([msg]);
        else if(solted[solted.length - 1][0].createdAt.slice(0, 10) !== msg.createdAt.slice(0, 10)) {
          solted.push([msg]);
        } else {
          solted[solted.length - 1].push(msg);
        }
    });
  
    return solted;
  };