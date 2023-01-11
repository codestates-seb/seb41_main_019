import { BrowserRouter, Routes, Route } from "react-router-dom";
import SideBar from "../src/components/public/Sidebar";

function App() {
  return (
    <BrowserRouter>
      <SideBar />
      <Routes>{/* <Route path={"/"} element={<App />} /> */}</Routes>
    </BrowserRouter>
  );
}

export default App;
