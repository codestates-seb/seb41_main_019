import { BrowserRouter, Routes, Route } from "react-router-dom";
import SideBar from "../src/components/public/SideBar/SideBar";
import Home from "./page/Home";
import MyPage from "./page/MyPage";
import Landing from "./page/Landing";
import Setting from "./page/Setting";
import WritePost from "./components/public/Post/WritePost";
import Background from "./components/public/Background";
import { useState } from "react";
import Cookie from "./util/Cookie";

function App() {
  const [isCovered, setIsCovered] = useState(false);
  const [isLanded, setIsLanded] = useState(!new Cookie().get("authorization"));
  const [isPosted, setIsPosted] = useState(false);
  const handleIsCovered = () => setIsCovered(!isCovered); 
  const handleIsPosted = () => {
    setIsPosted(!isPosted);
    setIsCovered(!isCovered);
  }

  return (
    <BrowserRouter>
      <Background isCovered={isCovered} handleIsCovered={handleIsCovered} />
      {
        isLanded ? null : <SideBar setIsLanded={setIsLanded} handleIsPosted={handleIsPosted} />
      }
      { isPosted ? <WritePost handleIsPosted={handleIsPosted} />: null }
      <Routes>
        <Route
          path={isLanded ? null : "/"}
          element={<Home handleIsCovered={handleIsCovered} />}
        />
        <Route
          path={isLanded ? null : "/mypage"}
          element={<MyPage handleIsCovered={handleIsCovered} />}
        />
        <Route path={isLanded ? "/" : null} element={<Landing setIsLanded={setIsLanded} isLanded={isLanded} />} />
        <Route path={isLanded ? null :"/setting"} element={<Setting />}
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
