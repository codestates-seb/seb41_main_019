import { useEffect, useState } from "react";
import styled from "styled-components";
import { alertDatas } from "../../../assets/dummyData/alertData";

const StyledAlertDiv = styled.div`
  display: flex;
  flex-direction: column;
  width: 350px;
  height: 100%;
  padding: 21px 20px 0px 20px;
  gap: 20px;

  > p {
    font-size: 18px;
    font-weight: 600;
    margin: 0px;
  }

  > p::after {
    content: "";
    display: block;
    width: 400px;
    margin: 20px 0px 0px -20px;
    border-top: 1px solid #dbdbdb;
  }

  div p {
    font-size: 16px;
    font-weight: 600;
    margin: 0px 0px 20px 0px
  }
`;

const StyledAlerts = styled.ul`
  margin: 0px;
  padding: 0px;
  list-style: none;
`

const StyledAlert = styled.li`
  display: flex;
  align-items: center;
  padding: 5px 0px 5px 0px;
  
  img {
    width: 24px;
    height : 24px;
    border-radius : 50px;
  }

  span {
    font-size: 14px;
    font-weight: 300;
  }
`

const Alert = () => {
  const [alertLog, setAlertLog] = useState({});

  const soltAlert = () => {
    const solted = {
      message : [],
      post : [],
      like : []
    }

    alertDatas.forEach(data => {
      solted[data.type].push(data);
    });

    setAlertLog(solted);
  }

  useEffect(() => {
    soltAlert();
  }, [])

  return (
      <StyledAlertDiv>
          <p>알림</p>
          {
            alertLog.message ?
            <div>
              <p>내 채팅</p>
              <StyledAlerts>
              {
                alertLog.message.map((data, idx) => {
                  return (
                    <StyledAlert key={idx}>
                      <div>
                        <img src={data.profileImg} alt="img" /></div>
                      <span>{data.username} 님이 새로운 채팅을 보냈습니다.</span>
                    </StyledAlert>
                  )
                })
              }
              </StyledAlerts>
            </div> : null
          }
          {
            alertLog.post ?
            <div>
              <p>내 게시물</p>
              <StyledAlerts>
                {
                  alertLog.post.map((data, idx) => {
                    return (
                      <StyledAlert key={idx}>
                        <img src={data.profileImg} alt="img" />
                        <span>{data.username} 님이 회원님의 게시물을 좋아합니다.</span>
                      </StyledAlert>
                    )
                  })
                }
              </StyledAlerts>
            </div> : null
          }
          {
            alertLog.like ? 
            <div>
              <p>내 프로필</p>
              <StyledAlerts>
                {
                  alertLog.like.map((data, idx) => {
                    return (
                      <StyledAlert key={idx}>
                        <img src={data.profileImg} alt="img" />
                        <span>{data.username} 님이 회원님을 팔로우했습니다. </span>
                      </StyledAlert>
                    )
                  })
                }
              </StyledAlerts> 
            </div>: null
          }
      </StyledAlertDiv>
  );
}

export default Alert;