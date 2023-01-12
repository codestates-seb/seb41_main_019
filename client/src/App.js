import { BrowserRouter, Routes, Route } from "react-router-dom";
import SideBar from "../src/components/public/Sidebar";
import Home from "./page/Home";
import MyPage from "./page/MyPage";

function App() {
  return (
    <BrowserRouter>
      <SideBar />
      <Routes>
        <Route path={"/"} element={<Home />} />
        <Route path={"/mypage"} element={<MyPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
