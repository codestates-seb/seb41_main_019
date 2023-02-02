import { useEffect, useRef } from "react";
import styled from "styled-components";
import { useAlert } from "../../../hooks/useAlert";
import { AiOutlineDelete } from "react-icons/ai"
import useModal from "../../../hooks/useModal";

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

  > span {
    font-size: 12pt;
    font-weight: 100;
    font-color: gray;
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
    cursor: pointer;
  }
`

const StyledButton = styled.button`
  display: flex;
  align-items: center;
  color: gray;
  border: 1px solid gray;
  border-radius: 3px;
  width: 28%;
  margin: 0px 0px 0px auto;
  font-size: 10pt;

  :hover {
    background-color: #dbdbdb;
    cursor: pointer;
  }

  svg {
    font-size: 14pt;
  }
`

const Alert = () => {
  const alert = useRef(null);
  const {log, setLog, connect, soltText, newLog, confirmAlert} = useAlert(alert);
  const { open, Modal } = useModal(false);

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
          <StyledButton onClick={(e) => {
            e.stopPropagation();
            confirmAlert();
          }}>
            <AiOutlineDelete />전체 확인
          </StyledButton>
          { log.length < 1 && <span>알림이 존재하지 않습니다.</span>}
          <StyledAlertUl ref={alert}>
          {
            log.map((data, idx) => {
              return (
                <StyledAlert key={idx} onClick={open}>
                  <img src={data.profileImage} alt="img"/>
                  <span>{data.userName}{soltText(data.sseType)}</span>
                </StyledAlert>
              )
            })
          }
          </StyledAlertUl>
          <Modal>
            <p>알림에서 즉시 이동은 현재 구현되지 않았습니다.</p>
          </Modal>
      </StyledAlertDiv>
  );
}

export default Alert;