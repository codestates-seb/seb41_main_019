import { BrowserRouter, Routes, Route } from "react-router-dom";
import SideBar from "../src/components/public/SideBar/SideBar";
import Home from "./page/Home";
import MyPage from "./page/MyPage";
import Chat from "./components/public/Chat/Chat";

function App() {
  return (
    <BrowserRouter>
      <SideBar />
      <Routes>
        <Route path={"/"} element={<Home />} />
        <Route path={"/mypage"} element={<MyPage />} />
      </Routes>
      <Chat />
    </BrowserRouter>
  );
}

export default App;
