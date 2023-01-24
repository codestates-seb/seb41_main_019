import styled from "styled-components";

const StyledMassage = styled.li`
  display: inline-flex;
  flex-direction: column;
  margin: 0px 0px 10px 0px;

  .msg {
    font-size : 12px;
    font-weight: 300;
    display: inline-block;
    margin: 0px;
    background-color: gray;
    border-radius: 5px;
    padding: 10px;
    overflow-wrap: anywhere;
  }

  .time {
    margin : 0px;
    font-size : 11px;
    font-weight: 300;
  }
`;

const Massage = ({ msg, user }) => {
  return (
    <StyledMassage className={Number(user) === msg.senderId ? "sender" : "receiver"}>
      <span className="msg">{msg.chat}</span>
      <span className="time">{msg.createdAt.slice(-8)}</span>
    </StyledMassage>
  );
};

export default Massage;
