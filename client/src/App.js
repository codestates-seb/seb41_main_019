import { BrowserRouter, Routes, Route } from "react-router-dom";
import SideBar from "../src/components/public/SideBar/SideBar";
import Home from "./page/Home";
import MyPage from "./page/MyPage";
import Landing from "./page/Landing";
import Setting from "./page/Setting";
import WritePost from "./components/public/Post/WritePost";
import Background from "./components/public/Background";
import { useEffect, useState } from "react";
import Cookie from "./util/Cookie";
import axios from "axios";
import { decode } from "./util/decode";

function App() {
  const [isCovered, setIsCovered] = useState(false);
  const [isLanded, setIsLanded] = useState(!new Cookie().get("authorization"));
  const [isPosted, setIsPosted] = useState(false);
  const [change, setChange] = useState(false);
  const cookie = new Cookie();

  const handleIsCovered = () => setIsCovered(!isCovered);

  const handleIsPosted = () => {
    setIsPosted(!isPosted);
    setIsCovered(!isCovered);
  };

  const handleChange = () => setChange(!change);

  useEffect(() => {
    document.body.style.overflow = isCovered ? "hidden" : null;
  }, [isCovered]);

  useEffect(() => {
    if(isLanded && cookie.get("refresh")) {
      axios({
        method: "post",
        url: `${process.env.REACT_APP_API}/members/reissues`,
        headers: { Refresh : cookie.get("refresh") }
      }).then((res) => {
            const date = new Date()
            const user = decode(res.headers.authorization);

            date.setMinutes(date.getMinutes() + 420);
            cookie.set("authorization", res.headers.authorization, { expires: date });
            cookie.set("memberId", user.memberId, { expires : date });
            cookie.set("username", user.username, { expires : date });

            setIsLanded(false);
      }).catch(e => {
        console.log(e);
      })
    }
  }, [isLanded])

  return (
    <BrowserRouter>
      {isCovered ? <Background isCovered={isCovered} /> : null}
      {isLanded ? null : (
        <SideBar setIsLanded={setIsLanded} handleIsPosted={handleIsPosted} change={change} setChange={setChange}/>
      )}
      {isPosted ? (
        <WritePost
          handleIsPosted={handleIsPosted}
          handleChange={handleChange}
        />
      ) : null}
      <Routes>
        <Route
          path={isLanded ? null : "/"}
          element={
            <Home
              handleIsCovered={handleIsCovered}
              change={change}
              handleChange={handleChange}
            />
          }
        />
        <Route
          path={isLanded ? null : "/mypage"}
          element={
            <MyPage isCovered={isCovered} change={change} handleIsCovered={handleIsCovered} handleChange={handleChange}  />
          }
        />
        <Route
          path={isLanded ? null : "/member"}
          element={
            <MyPage isCovered={isCovered} change={change} handleIsCovered={handleIsCovered} handleChange={handleChange}  />
          }
        />
        <Route
          path={isLanded ? "/" : null}
          element={<Landing setIsLanded={setIsLanded} isLanded={isLanded} />}
        />
        <Route path={isLanded ? null : "/setting"} element={<Setting setIsLanded={setIsLanded} />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
