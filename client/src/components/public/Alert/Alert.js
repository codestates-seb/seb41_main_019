import { useEffect, useRef } from "react";
import styled from "styled-components";
import { useAlert } from "../../../hooks/useAlert";

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

const StyledAlertUl = styled.ul`
  margin: 0px;
  padding: 0px;
  list-style: none;
`

const StyledAlert = styled.li`
  display: flex;
  align-items: center;
  padding: 5px 5px 5px 5px;
  border-radius: 3px;
  margin: 0px 0px 5px 0px;
  
  img {
    width: 24px;
    height : 24px;
    border-radius : 50px;
  }

  span {
    font-size: 14px;
    font-weight: 300;
  }

  :hover {
    background-color: #dbdbdb;
  }
`

const Alert = () => {
  const alert = useRef(null);
  const {log, setLog, patchLog, connect, soltText, newLog} = useAlert(alert);

  useEffect(() => {
    const sse = connect();

    return () => sse.close();
  }, [])

  useEffect(() => {
    if(newLog) {
      setLog([...log, newLog]);
    }
  }, [newLog])

  return (
      <StyledAlertDiv>
          <p>알림</p>
          <StyledAlertUl ref={alert}>
          {
            log.map((data, idx) => {
              return (
                <StyledAlert key={idx}>
                  <img src={data.profileImage} alt="img"/>
                  <span>{data.userName}{soltText(data.sseType)}</span>
                </StyledAlert>
              )
            })
          }
          </StyledAlertUl>
      </StyledAlertDiv>
  );
}

export default Alert;