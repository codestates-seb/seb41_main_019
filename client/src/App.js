import { BrowserRouter, Routes, Route } from "react-router-dom";
import SideBar from "../src/components/public/SideBar/SideBar";
import Home from "./page/Home";
import MyPage from "./page/MyPage";
import Landing from "./page/Landing";
import Background from "./components/public/Background";
import { useState } from "react";

function App() {
  const [isCovered, setIsCovered] = useState(false);
  const [isLanded, setIsLanded] = useState(false);
  const handleIsCovered = () => setIsCovered(!isCovered); 

  return (
    <BrowserRouter>
      <Background isCovered={isCovered} handleIsCovered={handleIsCovered} />
      {
        isLanded ? null : <SideBar setIsLanded={setIsLanded} />
      }
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
      </Routes>
    </BrowserRouter>
  );
}

export default App;
