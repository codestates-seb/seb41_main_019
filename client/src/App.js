import { BrowserRouter, Routes, Route } from "react-router-dom";
import SideBar from "../src/components/public/Sidebar";
import Home from "./page/Home";

function App() {
  return (
    <BrowserRouter>
      <SideBar />
      <Routes>
        <Route path={"/"} element={<Home />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
