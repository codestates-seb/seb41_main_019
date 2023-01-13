import { BrowserRouter, Routes, Route } from "react-router-dom";
import SideBar from "../src/components/public/Sidebar";
import Chat from "./components/public/Chat/Chat";
import Home from "./page/Home";
import MyPage from "./page/MyPage";
import Posting from "./page/Posting";
import Background from "./components/public/Background";
import { useState } from "react";

function App() {
  const [ isCovered, setIsCovered ] = useState(false);

  const handleIsCovered = () => setIsCovered(!isCovered);

  return (
    <BrowserRouter>
      <Background isCovered={isCovered} />
      <SideBar />
      <Routes>
        <Route path={"/"} element={<Home handleIsCovered={handleIsCovered} />} />
        <Route path={"/mypage"} element={<MyPage />} />
        <Route path={"/posting"} element={<Posting />} />
      </Routes>
      <Chat />
    </BrowserRouter>
  );
}

export default App;
