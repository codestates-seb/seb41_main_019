import { BrowserRouter, Routes, Route } from "react-router-dom";
import SideBar from "../src/components/public/SideBar/SideBar";
import Home from "./page/Home";
import MyPage from "./page/MyPage";
import Landing from "./page/Landing";
import Setting from "./page/Setting";
import Posting from "./components/public/Posting/Posting";
import Background from "./components/public/Background";
import { useState } from "react";

function App() {
  const [isCovered, setIsCovered] = useState(false);
  const [isLanded, setIsLanded] = useState(false);
  const [isPosted, setIsPosted] = useState(false);
  const handleIsCovered = () => setIsCovered(!isCovered); 
  const handleIsCovered = () => setIsCovered(!isCovered);
  const handleIsPosted = () => {
    setIsPosted(!isPosted);
    setIsCovered(!isCovered);
  }

  return (
    <BrowserRouter>
      <Background isCovered={isCovered} handleIsCovered={handleIsCovered} />
      {
        isLanded ? null : <SideBar setIsLanded={setIsLanded} />
      }
      { isPosted ? <Posting handleIsPosted={handleIsPosted} />: null }
      <SideBar handleIsPosted={handleIsPosted} />
      <Routes>
        <Route
          path={"/"}
          element={<Home handleIsCovered={handleIsCovered} />}
        />
        <Route
          path={"/mypage"}
          element={<MyPage handleIsCovered={handleIsCovered} />}
        />
        <Route path={"/landing"} element={<Landing />} />
        <Route
          path={"/setting"}
          element={<Setting />}
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
