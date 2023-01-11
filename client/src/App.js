import { BrowserRouter, Routes, Route } from "react-router-dom";
import SideBar from "../src/components/public/Sidebar";
import MyPage from "./page/MyPage";

function App() {
  return (
    <BrowserRouter>
      <SideBar />
      <Routes>
        <Route path={"/"} element={<MyPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
